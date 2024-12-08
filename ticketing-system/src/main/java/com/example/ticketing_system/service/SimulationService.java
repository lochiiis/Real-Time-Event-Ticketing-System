package com.example.ticketing_system.service;

import com.example.ticketing_system.cli.TicketPool;
import com.example.ticketing_system.cli.Vendor;
import com.example.ticketing_system.cli.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {

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
}
