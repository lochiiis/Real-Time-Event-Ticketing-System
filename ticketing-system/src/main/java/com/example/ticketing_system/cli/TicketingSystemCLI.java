package com.example.ticketing_system.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.example.ticketing_system.cli.Configuration.getValidInput;

/**
 * command-line interface for the real-time ticketing system
 * allows users to configure the system, start the simulation, or exit
 */
public class TicketingSystemCLI {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Real-Time Event Ticketing System");

        Configuration config; // Configuration object to store system settings


        while(true){
            try{
                // Taking configuration inputs
                int totalTickets = getValidInput(input, "Enter Total Number of Tickets: ", 1, Integer.MAX_VALUE);
                int ticketReleaseRate = getValidInput(input, "Enter Ticket Release Rate (tickets/sec): ", 1, Integer.MAX_VALUE);
                int customerRetrievalRate = getValidInput(input, "Enter Customer Retrieval Rate (tickets/sec): ", 1, Integer.MAX_VALUE);
                int maxTicketCapacity = getValidInput(input, "Enter Maximum Ticket Capacity: ", 1, Integer.MAX_VALUE);


                // Validate input values to ensure they are positive
                if (totalTickets > 0 && ticketReleaseRate > 0 && customerRetrievalRate > 0 && maxTicketCapacity >0) {

                    //initialize configuration object
                    config =new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

                    config.saveToFile("config.json");

                    config.writeLogs("\n----------------------------------------------------------------------\n");
                    config.writeLogs("Total Tickets: " + totalTickets);
                    config.writeLogs("TicketSales Release Rate: " + ticketReleaseRate);
                    config.writeLogs("Customer Retrieval Rate: " + customerRetrievalRate);
                    config.writeLogs("Maximum TicketSales Capacity: " + maxTicketCapacity);
                    config.writeLogs("\n----------------------------------------------------------------------\n");


                    try {
                        System.out.println("Successfully saved configurations to file");
                        break; // exit loop after saving the configurations
                    } catch (Exception e) {
                        System.out.print("Error while saving configurations to file");
                    }
                }else {
                    System.out.println("Invalid input. Ensure all values are positive and Max Capacity >= Total Tickets");
                }

            }catch(InputMismatchException e){
                System.out.println("Invalid input");
            }
        }

        // Initialize the TicketPool with the user-defined configuration
        TicketPool ticketPool=new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        //Start or exit system
        while(true) {
            System.out.print("""
                    1.start the system (y)
                    2.exit the system (n)
                    Select option:""");
            String option = input.next().toLowerCase();

            if (option.equals("y")) {

                //start vendor threads
                for (int i = 1; i < 6; i++) {
                    Thread vendorThread = new Thread(new Vendor(i, 2, config.getTicketReleaseRate(), ticketPool));
                    vendorThread.start();
                }

                //start consumer threads
                for (int i = 1; i < 6; i++) {
                    Thread customerThread = new Thread(new Customer(i, config.getCustomerRetrievalRate(), ticketPool));
                    customerThread.start();
                }
                break; //exit loop after starting the system

            } else if (option.equals("n")) {
                System.out.println("System exiting...");
                System.exit(0);

            } else {
                System.out.println("Invalid input.Please enter 'y' to start or 'n' to exit");
            }
        }
    }
}
