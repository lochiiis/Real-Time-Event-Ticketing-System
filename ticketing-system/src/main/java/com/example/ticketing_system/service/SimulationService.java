package com.example.ticketing_system.service;

import com.example.ticketing_system.cli.Configuration;
import com.example.ticketing_system.cli.TicketPool;
import com.example.ticketing_system.cli.Vendor;
import com.example.ticketing_system.cli.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimulationService {
    /**
     * Variables to track the number of tickets in different states.
     * values are used to update and show the ticket sales chart.
     */
    private int totalTickets;
    private int ticketReleased=0;
    private int ticketsBought=0;
    private int ticketsRemaining=0;



    private TicketPool ticketPool;  // Reference to the ticket pool object that manages the tickets
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();
    private boolean running = false;  // flag to check if the simulation is currently running




    /**
     * Start simulation and initialize ticket pool, vendors, and customers
     *
     * @param totalTickets              total number of tickets available
     * @param ticketReleaseRate         rate at which vendors release tickets (second)
     * @param customerRetrievalRate     rate at which customers try to retrieve tickets (second)
     * @param maxCapacity               maximum capacity of the ticket pool
     */
    public void startSimulation(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxCapacity) {
        if (running){
            stopSimulation(); // Stop any ongoing simulation before starting a new one
        }

        //initialize ticket pool and configuration
        ticketPool = new TicketPool(maxCapacity, totalTickets);
        Configuration config=new Configuration(totalTickets,ticketReleaseRate,customerRetrievalRate,maxCapacity);
        config.saveToFile("config.json");

        // Clear any previous logs from the ticket pool before starting a new simulation
        ticketPool.clearLogs();

        // Start 5 vendor threads that will release tickets into the pool
        for (int i = 1; i < 6; i++) {
            Thread vendorThread = new Thread(new Vendor(i, 2, ticketReleaseRate, ticketPool));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }


        // Start 5 customer threads that will attempt to retrieve tickets from the pool
        for (int i = 1; i < 6; i++) {
            Thread customerThread = new Thread(new Customer(i, customerRetrievalRate, ticketPool));
            customerThreads.add(customerThread);
            customerThread.start();
        }
        // Mark the simulation as running
        running = true;
    }


    /**
     * Stop the simulation and interrupt vendor and customer threads
     * clears the thread lists and marks the simulation as stopped
     */
    public void stopSimulation() {
        // If the simulation is already stopped, do nothing
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

    /**
     *  Fetches the simulation logs from the ticket pool
     *  Returns an empty list if the ticket pool has not been initialized yet
     *
     * @return List of simulation log entries
     */
    public List<String> getSimulationLogs() {
        // If the ticket pool is not initialized, return an empty list
        if (ticketPool == null) {
            return new ArrayList<>();
        }
        return ticketPool.getLogs(); // Return the logs from the ticket pool
    }


    /**
     * Collects and returns real-time analytics data for the ticketing system
     * includes the total number of tickets, tickets released, tickets bought, and remaining tickets
     *
     * @return A mapping of the analytics data
     */
    public Map<String,Integer>getAnalytics(){
        // if the ticket pool is not initialized, return default analytics values
        if(ticketPool == null){
            Map<String,Integer> defaultAnalytics=new HashMap<>();
            defaultAnalytics.put("totalTickets",0);
            defaultAnalytics.put("ticketsReleased",0);
            defaultAnalytics.put("ticketsBought",0);
            defaultAnalytics.put("ticketsRemaining",0);
            return defaultAnalytics;
        }

        // fetch real-time data from the ticket pool
        totalTickets=ticketPool.getTotalTickets();
        ticketReleased=ticketPool.getTotalTicketsAdded();
        ticketsBought=ticketReleased-ticketPool.getRemainingTickets();
        ticketsRemaining=ticketPool.getRemainingTickets();


        //create a map to store the analytics data
        Map<String,Integer> analytics=new HashMap<>();

        analytics.put("totalTickets",totalTickets);
        analytics.put("ticketsReleased",ticketReleased);
        analytics.put("ticketsBought",ticketsBought);
        analytics.put("ticketsRemaining",ticketsRemaining);

        return analytics;// Return the collected analytics

    }

}
