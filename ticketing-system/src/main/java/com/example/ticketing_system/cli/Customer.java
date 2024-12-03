package com.example.ticketing_system.cli;

public class Customer implements Runnable {

    private final int customerId;
    private final int retrievalInterval;
    private final TicketPool ticketPool;


    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(retrievalInterval*1000);
                ticketPool.removeTicket(customerId);
            } catch (InterruptedException e) {
                System.out.println("Customer interrupted");
                break;
            }
        }
    }
}
