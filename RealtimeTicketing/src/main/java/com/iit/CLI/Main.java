package com.iit.CLI;

import java.util.*;
import java.lang.module.Configuration;
import java.net.spi.InetAddressResolverProvider;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        WebConfiguration config = new WebConfiguration ();
        System.out.println("Welcome to the Real-Time ticket booking system");

        // Total Tickets
        System.out.print("Enter the total number of tickets: ");
        config.setTotalTickets(validatePositiveInput(scanner));


        // Ticket Release Rate
        System.out.print("Please enter the ticket release rate (tickets): ");
        config.setTicketReleaseRate(validatePositiveInput(scanner));


        // Ticket Retrieval Rate
        System.out.print("Please enter the ticket retrieval rate (tickets): ");
        config.setCustomerRetrievalRate(validatePositiveInput(scanner));

        // Maximum Ticket Capacity
        System.out.print("Please enter the maximum ticket capacity: ");
        config.setMaxTicketCapacity(validatePositiveInput(scanner));

        // Validating configuration and saving to a file
        if(config.validate()){
            System.out.println("\nConfiguration is Validated");

            config.saveToFile("config.json");

            // Load configuration back from the file
            WebConfiguration loadedConfig = WebConfiguration.loadFromFile("config.json");
            if(loadedConfig != null){
                System.out.println("\nConfiguration is Loaded");
                System.out.println("Total Tickets: " + loadedConfig.getTotalTickets());
                System.out.println("Ticket Release Rate: " + loadedConfig.getCustomerRetrievalRate());
                System.out.println("Customer Retrieval Rate: " + loadedConfig.getCustomerRetrievalRate());
                System.out.print("Maximum Ticket Capacity: " + loadedConfig.getMaxTicketCapacity() + "\n");
                System.out.println(" ");
            }
        }
        else{
            System.out.println("\nConfiguration is not Validated, Please try again.");
        }
        scanner.close();

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity()); // The TicketPool which is shared among Vendors and Customers

        TicketVendor[] vendors = new TicketVendor[10]; // Array of Vendors, for convienence i have used an array of objects
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new TicketVendor(ticketPool, config.getTicketReleaseRate(), config.getCustomerRetrievalRate());
            Thread vendorThread = new Thread(vendors[i], "vendor-" + i); // Used 3rd Constructor of the Thread class
            vendorThread.start(); // Start the Vendor Thread
        }

        Customer[] customers = new Customer[10];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(ticketPool, config.getCustomerRetrievalRate(), config.getTotalTickets());
            Thread customerThread = new Thread(customers[i], "customer-" + i); // Used 3rd Constructor of the Thread class
            customerThread.start(); // Start the Customer Thread
        }
    }

    // Method to validate positive integer input
    private static int validatePositiveInput(Scanner scanner) {
        int value = -1;
        while (value <= 0) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value <= 0) {
                    System.out.print("Please enter a positive number: ");
                }
            }
            else {
                System.out.println("Invalid input. Please enter a positive number: ");
                scanner.next(); // Clear the invalid input
            }
        }
        return value;
    }
}