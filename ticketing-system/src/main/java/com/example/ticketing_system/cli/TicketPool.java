package com.example.ticketing_system.cli;



import java.util.*;


public class TicketPool {
    private final List<Integer> ticketList = Collections.synchronizedList(new LinkedList<>());
    private int maxCapacity;
    private final Random random = new Random();
    private final int totalTickets;
    private int totalTicketsAdded = 0;

    private int ticketId=1;

    Configuration config = new Configuration();

    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());


    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
    }

//    int count=0;
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
                    ticketList.add(ticketId++);

                    System.out.println(ticketList);
//                    count++;
//                    System.out.println(count);
                }
                totalTicketsAdded += ticketCount;

                String outputMsg = "Vendor" + vendorId + " has added " + ticketCount + " tickets. Current size: " + ticketList.size();
                System.out.println(outputMsg);
                config.writeLogs(outputMsg);
                logs.add(outputMsg);


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
                    String outputMsg="No tickets available. Customer-" + customerId + " is waiting...";
                    System.out.println(outputMsg);
                    logs.add(outputMsg);
                    ticketList.wait();
                }
                int index = random.nextInt(ticketList.size());
                int removedTicketId = ticketList.get(index); // if remove this gives index out of bound

                ticketList.remove(index);

               String outputMsg= "customer-"+customerId+" has removed ticketId:"+ removedTicketId +" from the pool.Current size is "+ticketList.size();
               System.out.println(outputMsg);
               config.writeLogs(outputMsg);
               logs.add(outputMsg);

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

    // Clear logs
    public void clearLogs() {
        logs.clear();
    }


}
