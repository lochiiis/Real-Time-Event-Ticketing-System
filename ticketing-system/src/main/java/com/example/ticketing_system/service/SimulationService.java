package com.example.ticketing_system.service;

import com.example.ticketing_system.cli.Configuration;
import com.example.ticketing_system.cli.TicketPool;
import com.example.ticketing_system.cli.Vendor;
import com.example.ticketing_system.cli.Customer;
import com.example.ticketing_system.repository.TicketSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimulationService {
    /**
     * used to show the visual charts
     */
    private int totalTickets;
    private int ticketReleased=0;
    private int ticketsBought=0;
    private int ticketsRemaining=0;


    @Autowired
    private TicketSalesRepository ticketSalesRepository;

    private TicketPool ticketPool;
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();
    private boolean running = false;

    // Start simulation and initialize ticket pool, vendors, and customers
    public void startSimulation(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxCapacity) {
        if (running){
            stopSimulation(); // Stop any ongoing simulation before starting a new one
        }

        ticketPool = new TicketPool(maxCapacity, totalTickets);
        Configuration config=new Configuration(totalTickets,ticketReleaseRate,customerRetrievalRate,maxCapacity);
        config.saveToFile("config.json");

        // Clear logs before starting a new simulation
        ticketPool.clearLogs();

        // Start vendor threads
        for (int i = 1; i < 6; i++) {
            Thread vendorThread = new Thread(new Vendor(i, 2, ticketReleaseRate, ticketPool));
            vendorThreads.add(vendorThread);
            vendorThread.start();

        }


        // Start customer threads
        for (int i = 1; i < 6; i++) {
            Thread customerThread = new Thread(new Customer(i, customerRetrievalRate, ticketPool));
            customerThreads.add(customerThread);
            customerThread.start();
        }

        running = true;
    }

    // Stop the simulation and interrupt vendor and customer threads
    public void stopSimulation() {
        if (!running) return;

        for (Thread thread : vendorThreads) {
            thread.interrupt();
        }
        for (Thread thread : customerThreads) {
            thread.interrupt();
        }

        // clear thread lists and mark simulation as stopped
        vendorThreads.clear();
        customerThreads.clear();
        running = false;
    }

    // fetch logs if ticketPool is initialized
    public List<String> getSimulationLogs() {
        if (ticketPool == null) {
            return new ArrayList<>(); // return empty if ticketPool is not initialized
        }
        return ticketPool.getLogs();
    }





    public Map<String,Integer>getAnalytics(){
        if(ticketPool == null){
            Map<String,Integer> defaultAnalytics=new HashMap<>();
            defaultAnalytics.put("totalTickets",0);
            defaultAnalytics.put("ticketsReleased",0);
            defaultAnalytics.put("ticketsBought",0);
            defaultAnalytics.put("ticketsRemaining",0);
            return defaultAnalytics;
        }

        //fetch real-time data
        totalTickets=ticketPool.getTotalTickets();
        ticketReleased=ticketPool.getTotalTicketsAdded();
        ticketsBought=ticketReleased-ticketPool.getRemainingTickets();
        ticketsRemaining=ticketPool.getRemainingTickets();


        //adding sales data to the chart
        Map<String,Integer> analytics=new HashMap<>();

        analytics.put("totalTickets",totalTickets);
        analytics.put("ticketsReleased",ticketReleased);
        analytics.put("ticketsBought",ticketsBought);
        analytics.put("ticketsRemaining",ticketsRemaining);
        return analytics;

    }

}
