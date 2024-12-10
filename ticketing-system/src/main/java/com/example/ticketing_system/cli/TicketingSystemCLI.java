package com.example.ticketing_system.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketingSystemCLI {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Real-Time Event Ticketing System");

        Configuration config=null;
        int releaseTickets;

        while(true){
            try{
                // Taking configuration inputs
                System.out.print("Enter Total Number of Tickets: ");
                int totalTickets = input.nextInt();

                System.out.print("Enter TicketSales Release Rate (tickets/sec): ");
                int ticketReleaseRate = input.nextInt();

                System.out.print("Enter Customer Retrieval Rate (tickets/sec): ");
                int customerRetrievalRate = input.nextInt();

                System.out.print("Enter Maximum TicketSales Capacity: ");
                int maxTicketCapacity = input.nextInt();

                System.out.print("Enter number of tickets released by a vendor at a time: ");
                releaseTickets = input.nextInt();

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
                        break;
                    } catch (Exception e) {
                        System.out.print("Error while saving configurations to file");
                    }
                }else {
                    System.out.println("Invalid input. Ensure all values are positive and Max Capacity >= Total Tickets");
                }

            }catch(InputMismatchException e){
                System.out.println("invalid input");
                return;
            }
        }

        TicketPool ticketPool=new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        System.out.print("enter y to start the system :");
        String option=input.next();
        if(option.equals("y")){

            for (int i = 1; i < 6; i++) {
                Thread vendorThread = new Thread(new Vendor(i,releaseTickets,config.getTicketReleaseRate(),ticketPool));
                vendorThread.start();
            }

            for (int i = 1; i < 6; i++) {
                Thread customerThread = new Thread(new Customer(i,config.getCustomerRetrievalRate(),ticketPool));
                customerThread.start();
            }


        }else if (option.equals("n")) {
            System.out.println("System exiting...");
            System.exit(0);

        }else {
            System.out.println("Invalid input exiting system");
        }

    }
}
