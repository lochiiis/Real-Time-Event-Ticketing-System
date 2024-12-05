package com.example.ticketing_system.cli;

import java.util.Scanner;

public class TicketingSystemCLI {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Real-Time Event Ticketing System");

        Configuration config;

        while(true){
            try{
                // Taking configuration inputs
                System.out.print("Enter Total Number of Tickets: ");
                int totalTickets = input.nextInt();

                System.out.print("Enter Ticket Release Rate (tickets/sec): ");
                int ticketReleaseRate = input.nextInt();

                System.out.print("Enter Customer Retrieval Rate (tickets/sec): ");
                int customerRetrievalRate = input.nextInt();

                System.out.print("Enter Maximum Ticket Capacity: ");
                int maxTicketCapacity = input.nextInt();

                if (totalTickets > 0 && ticketReleaseRate > 0 && customerRetrievalRate > 0 && maxTicketCapacity >0) {
                    //save configurations
                    config =new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
                    try {
                        config.saveToFile("config.json");
                        System.out.println("Successfully saved config to file");
                        break;
                    } catch (Exception e) {
                        System.out.print("Error while saving config to file");
                    }
                }else {
                    System.out.println("Invalid input. Ensure all values are positive and Max Capacity >= Total Tickets");
                }

            }catch(NumberFormatException e){
                System.out.println("invalid input");
            }
        }

        TicketPool ticketPool=new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        System.out.print("enter y to start the system :");
        String option=input.next();
        if(option.equals("y")){

            for (int i = 0; i < 5; i++) {
                Thread vendorThread = new Thread(new Vendor(i,2,config.getTicketReleaseRate(),ticketPool));
                vendorThread.start();
            }

            for (int i = 0; i < 5; i++) {
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
