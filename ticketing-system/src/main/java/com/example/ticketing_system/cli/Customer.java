package com.example.ticketing_system.cli;

public class Customer implements Runnable {

    private final int customerId;
    private final int retrievalInterval;
    private final TicketPool ticketPool;

    /**
     * Each customer attempts to remove tickets from the ticket pool at a specified retrieval rate.
     *
     * @param customerId  unique identifier for the customer
     * @param retrievalInterval Time interval (in seconds) between each ticket retrieval attempt
     * @param ticketPool Shared ticket pool where tickets are removed from
     */
    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    /**
     * continuously attempts to remove a ticket from the ticket pool at specified intervals.
     */
    @Override
    public void run() {
        try{
            while (true) {
                ticketPool.removeTicket(customerId);
                Thread.sleep(retrievalInterval*1000);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("Customer"+ customerId + " interrupted");
        }
    }
}
