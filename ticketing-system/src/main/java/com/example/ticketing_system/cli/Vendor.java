package com.example.ticketing_system.cli;

public class Vendor implements Runnable {
    private  final int vendorId;
    private final int ticketsPerRelease; //num of tickets released per interval
    private final int releaseInterval; //interval in ms
    private final TicketPool ticketPool;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(releaseInterval*1000);
                ticketPool.addTicket(ticketsPerRelease,vendorId); //add tickets to the pool
            }catch(InterruptedException e){
                System.out.println("vendor"+" interrupted");
                break;
            }
        }
    }
}
