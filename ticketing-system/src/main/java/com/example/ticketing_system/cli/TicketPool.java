package com.example.ticketing_system.cli;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final List<String> ticketList = Collections.synchronizedList(new ArrayList<>());
    private final int maxCapacity;
    private final Random random = new Random();
    private final AtomicInteger ticketIdCounter = new AtomicInteger(1); // Thread-safe counter


    public TicketPool(int maxCapacity,int totalTickets) {
        this.maxCapacity = maxCapacity;

        //preload tickets
        synchronized (ticketList) {
            for(int i = 0; i < totalTickets && ticketList.size()< maxCapacity; i++) {
                String ticket="TicketId:"+ ticketIdCounter.getAndIncrement();
                ticketList.add(ticket);
            }
        }
    }



    public void addTicket(int num,int vendorId){
        synchronized (ticketList) {
            for(int i=0; i<num; i++){
                if(ticketList.size() < maxCapacity){
                    String ticket="TicketId:" + ticketIdCounter.getAndIncrement();
                    ticketList.add(ticket);
                    System.out.println("vendor-"+vendorId+" has added "+ ticket +" to the pool. Current size is " +ticketList.size());
//                    System.out.println(Arrays.toString(ticketList.toArray()));
                }else{
                    System.out.println("TicketPool capacity reached.Can't add more tickets");
                    break;
                }
            }
        }
    }

    public void removeTicket(int customerId){
        synchronized (ticketList) {

            if(!ticketList.isEmpty()){
                int index = random.nextInt(ticketList.size());
                String removedTicket=ticketList.remove(index);
                System.out.println("customer-"+customerId+" has removed "+ removedTicket +" from the pool.Current size is "+ticketList.size());
            }else{
                System.out.println("No tickets available to be purchased");
            }
        }
    }


}
