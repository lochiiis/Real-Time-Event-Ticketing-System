package com.example.ticketing_system.cli;



import java.util.*;
import java.util.logging.Logger;


public class TicketPool {
    private final List<Integer> ticketList = Collections.synchronizedList(new LinkedList<>());
    private int maxCapacity;
    private final Random random = new Random();
    private final int totalTickets;
    private int totalTicketsAdded = 0;


    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());


    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
    }

    int count=0;
    public void addTicket(int ticketCount, int vendorId){
        synchronized (ticketList) {
            try{
                while(ticketList.size()+ticketCount > maxCapacity || totalTicketsAdded + ticketCount > totalTickets){ //if the vendor wants to add tickets and the list exceeds
                    if (totalTicketsAdded > totalTickets) {
                        return; // Exit without waiting, as no more tickets can be added.
                    }
//                    System.out.println("TicketPool capacity reached.Can't add more tickets");
                    ticketList.wait();
                }
                for(int i=0; i<ticketCount;i++){
                    ticketList.add(1);
                    count++;
                    System.out.println(count);
                }
                totalTicketsAdded += ticketCount;
                System.out.println("Vendor"+ vendorId+ " has added "+ ticketCount +" tickets.current size is "+ticketList.size());

                String log = "Vendor" + vendorId + " has added " + ticketCount + " tickets. Current size: " + ticketList.size();
                logs.add(log);


                ticketList.notifyAll();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
//                System.out.println("Vendor"+ vendorId +" interrupted while adding tickets");
            }
        }


    }

    public void removeTicket(int customerId){
        synchronized (ticketList) {
            try{
                while(ticketList.isEmpty()){
                    System.out.println("No tickets available. Customer-" + customerId + " is waiting...");
                    ticketList.wait();
                }
                int index = random.nextInt(ticketList.size());
                ticketList.remove(index);
               System.out.println("customer-"+customerId+" has removed ticketId:"+ index +" from the pool.Current size is "+ticketList.size());

                String log = "Customer-" + customerId + " has removed ticketId: " + index + ". Current size: " + ticketList.size();
                logs.add(log);


                ticketList.notifyAll();

            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
//                System.out.println("Customer" + customerId + "interrupted while removing tickets");
            }
        }

    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }


}
