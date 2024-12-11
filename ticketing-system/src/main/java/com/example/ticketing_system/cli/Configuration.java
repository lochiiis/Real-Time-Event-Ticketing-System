package com.example.ticketing_system.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Configuration {

    private int totalTickets; // Total number of tickets in the system

    private int ticketReleaseRate; // Rate at which tickets are released by vendors (second)

    private int customerRetrievalRate; // Rate at which customers attempt to retrieve tickets (second)

    private int maxTicketCapacity; // Maximum capacity of the ticket pool

    //default constructor
    public Configuration() {
    }

    // Parameterized constructor to initialize configuration with given values
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



    /**
     * Ensures that the value entered is within the specified range.
     *
     * @param input     Scanner object for taking user input
     * @param prompt    message displayed to the user
     * @param minValue  minimum acceptable value
     * @param maxValue  maximum acceptable value
     * @return          valid input
     */
    public static int getValidInput(Scanner input, String prompt, int minValue, int maxValue) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = input.nextInt();
                if (value >= minValue && value <= maxValue) {
                    return value;
                } else {
                    System.out.println("Please enter a value grater than zero");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.nextLine();
            }
        }
    }


    /**
     * Saves the current configuration to a JSON file
     *
     * @param filename the name of the file where configuration will bee saved
     */

    public void saveToFile(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();// Create Gson object for serialization
        try(Writer writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            System.out.println("error in writing to file");
        }
    }

    /**
     * Writes log messages with timestamps to a log file
     *
     * @param msg the log message be written
     */
    public void writeLogs(String msg) {
        String timeStamp= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try(BufferedWriter writer= new BufferedWriter(new FileWriter("TicketingSystemLogs.txt",true))){

           writer.write(timeStamp+" - " +msg +"\n");
        } catch (IOException e) {
            System.out.println("Error in writing to file");
        }
    }

}

