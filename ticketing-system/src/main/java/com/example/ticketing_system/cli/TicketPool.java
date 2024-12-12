package com.example.ticketing_system.cli;

import java.util.*;

/**
 * ensures thread-safe operations for adding and removing tickets by vendors and customers
 */
public class TicketPool {
    private final List<Integer> ticketList = Collections.synchronizedList(new LinkedList<>()); // thread-safe list to store tickets
    private int maxCapacity; // maximum capacity of the ticket pool
    private final Random random = new Random(); // random generator for ticket removal
    private final int totalTickets; //total number of tickets allowed in the system
    private int totalTicketsAdded = 0; //tracks the total number of tickets added to the pool

    private int ticketId=1; // unique identifier for each ticket

    Configuration config = new Configuration();

    // Thread-safe list to store logs
    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());



    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
    }


    /**
     * Adds tickets to the pool in a thread-safe manner
     *
     * @param ticketCount number of tickets to add
     * @param vendorId ID of the vendor adding tickets
     */
    public void addTicket(int ticketCount, int vendorId){
        synchronized (ticketList) {
            try{
                //wait if adding tickets exceeds max capacity or total tickets
                while(ticketList.size()+ticketCount > maxCapacity || totalTicketsAdded + ticketCount > totalTickets){ //if the vendor wants to add tickets and the list exceeds
                    if (totalTicketsAdded > totalTickets) {
                        return; // Exit without waiting, as no more tickets can be added.
                    }
                    ticketList.wait();
                }

                // add tickets to the pool
                for(int i=0; i<ticketCount;i++){
                    ticketList.add(ticketId++); //assign unique ticket ID
                }
                totalTicketsAdded += ticketCount;


                String outputMsg = "Vendor" + vendorId + " has released " + ticketCount + " tickets. Current released tickets: " + ticketList.size();


                System.out.println(outputMsg);
                config.writeLogs(outputMsg); //write the log details in text file
                logs.add(outputMsg); //add to list of log entries

                ticketList.notifyAll();

            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }


    /**
     * Removes a ticket from the pool in a thread-safe manner
     *
     * @param customerId   ID of the customer attempting to remove a ticket
     */
    public void removeTicket(int customerId){
        synchronized (ticketList) {
            try{
                // wait if no tickets are available
                while(ticketList.isEmpty()){
                    String outputMsg="No tickets available. Customer-" + customerId + " is waiting...";
                    System.out.println(outputMsg);
                    config.writeLogs(outputMsg);//write the log details in text file
                    logs.add(outputMsg);//add to list of log entries
                    ticketList.wait();
                }

                //remove a tickets from a random index
                int index = random.nextInt(ticketList.size());
                int removedTicketId = ticketList.get(index);
                ticketList.remove(index);


                String outputMsg= "customer"+customerId+" has bought ticket of ticketId:"+ removedTicketId +". Current available tickets: "+ticketList.size();
                System.out.println(outputMsg);
                config.writeLogs(outputMsg);//write the log details in text file
                logs.add(outputMsg);//add to list of log entries

                ticketList.notifyAll();

            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }


    /**
     * returns logs from the ticket pool
     *
     * @return copy of logs as a list of strings
     */
    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * clears all logs in ticket pool
     */
    public void clearLogs() {
        logs.clear();
    }


    /**
     *  to return data for the visual charts
     */

    public synchronized int getTotalTicketsAdded() {
        return totalTicketsAdded;
    }

    public synchronized int getRemainingTickets() {
        return ticketList.size();
    }

    public int getTotalTickets() {
        return totalTickets;
    }

}
