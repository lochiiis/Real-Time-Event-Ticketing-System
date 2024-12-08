package com.example.ticketing_system.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Configuration {



    private int totalTickets;


    private int ticketReleaseRate;


    private int customerRetrievalRate;


    private int maxTicketCapacity;


    public Configuration() {
    }

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }


    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void saveToFile(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(Writer writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            System.out.println("error in writing to file");
        }
    }






    private final String LOG_FILE ="TicketingSystemLogs.txt";
    private boolean configurationLogged = false; // Flag to ensure configurations are logged only once


    public void writeLogs(String msg) {
        String timeStamp= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try(BufferedWriter writer= new BufferedWriter(new FileWriter(LOG_FILE,true))){
            if (!configurationLogged) {
                writer.write("\n----------------------------------------------------------------------\n");
                configurationLogged = true; // Mark configurations as logged
            }
           writer.write(timeStamp+" - " +msg +"\n");
        } catch (IOException e) {
            System.out.println("Error in writing to file");
        }
    }






}

