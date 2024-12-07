package com.example.ticketing_system.cli;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

import java.io.Writer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Configuration {

    private static Logger logger;

    @JsonProperty("totalTickets")
    private int totalTickets;

    @JsonProperty("ticketReleaseRate")
    private int ticketReleaseRate;

    @JsonProperty("customerRetrievalRate")
    private int customerRetrievalRate;

    @JsonProperty("maxTicketCapacity")
    private int maxTicketCapacity;



    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        writeLogger(); //initialize logger
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







    private void writeLogger() {
        if(logger == null) {
            try{
                logger=Logger.getLogger("TicketingSystemLogger");
                FileHandler fileHandler = new FileHandler("TicketingSystemLog.txt", true);
                fileHandler.setFormatter(new SimpleFormatter(){
                    @Override
                    public String format(java.util.logging.LogRecord record) {
                        return record.getMessage() + System.lineSeparator();
                    }
                });
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false); //prevent console logging
                logger.info("Logger initialized");
            }catch(IOException e){
                System.err.println("error in logging file"+e.getMessage());
            }
        }
    }

    public static Logger getLogger() {
        return logger;
    }




}

