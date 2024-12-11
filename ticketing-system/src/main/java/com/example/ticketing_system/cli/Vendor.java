package com.example.ticketing_system.cli;

public class Vendor implements Runnable {
    private  final int vendorId;
    private final int ticketsPerRelease; //num of tickets released per interval
    private final int releaseInterval; //interval in ms
    private final TicketPool ticketPool;


    /**
     *
     * @param vendorId  unique ID for the vendor.
     * @param ticketsPerRelease  number of tickets the vendor releases per interval
     * @param releaseInterval interval (seconds) between each ticket release
     * @param ticketPool shared ticket pool where tickets are added
     */
    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }


    /**
     *  continuously releases tickets into the ticket pool at the specified release interval
     */
    @Override
    public void run() {
        try{
            while (true){
                ticketPool.addTicket(ticketsPerRelease,vendorId);
                Thread.sleep(releaseInterval*1000);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("vendor"+ vendorId +" interrupted");

        }
    }
}
