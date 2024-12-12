package com.iit.RealtimeTicketing.CLI;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Main {
    private static volatile boolean running = true; // Control flag for simulation
    private static Thread simulationThread;

    public static void main(String[] args) {
        WebConfiguration config = new WebConfiguration();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to the Real-Time Ticket Booking System");

        boolean isValidConfig = false;
        while (!isValidConfig) {

            // Total Tickets
            System.out.print("\nEnter the total number of tickets: ");
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

            // Validating configuration
            if (config.validate()) {
                isValidConfig = true;
                System.out.println("\nConfiguration is Validated. Initializing the system.... ");
            } else {
                System.out.println("\nConfiguration is not Validated, Please try again.");
            }
        }

        // Saving Configuration to a file
        config.saveToFile("config.json");

        // Load configuration back from the file
        WebConfiguration loadedConfig = WebConfiguration.loadFromFile("config.json");
        if (loadedConfig != null) {
            System.out.println("Configuration is saved and Loaded successfully.");
            System.out.println("\nTotal Tickets: " + loadedConfig.getTotalTickets());
            System.out.println("Ticket Release Rate: " + loadedConfig.getTicketReleaseRate());
            System.out.println("Customer Retrieval Rate: " + loadedConfig.getCustomerRetrievalRate());
            System.out.print("Maximum Ticket Capacity: " + loadedConfig.getMaxTicketCapacity() + "\n");
            System.out.println(" ");
        } else {
            System.out.println("\nFailed to reload Configuration. Please try again.");
        }

        // Start the simulation menu
        simulationMenu(scanner, loadedConfig);
    }

    private static void simulationMenu(Scanner scanner, WebConfiguration loadedConfig) {
        while (true) {
            System.out.println("\nSimulation Menu:");
            System.out.println("1. Start Simulation");
            System.out.println("2. Stop Simulation");
            System.out.print("Enter your choice: ");

            int choice = validatePositiveInput(scanner);
            switch (choice) {
                case 1:
                    startSim(loadedConfig);
                    break;
                case 2:
                    stopSimulation();
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, Please select between options 1 and 2.");
            }
        }
    }

    public static void startSim(WebConfiguration loadedConfig) {
        if (simulationThread != null && simulationThread.isAlive()) {
            System.out.println("Simulation is already running.");
            return;
        }

        running = true; // Set the control flag to true

        simulationThread = new Thread(() -> {
            TicketPool ticketPool = new TicketPool(loadedConfig.getMaxTicketCapacity());
            TicketVendor[] vendors = new TicketVendor[10];
            Customer[] customers = new Customer[10];

            // Create and start vendor threads
            for (int i = 0; i < vendors.length; i++) {
                vendors[i] = new TicketVendor(ticketPool, loadedConfig.getTotalTickets(), loadedConfig.getTicketReleaseRate());
                int finalI = i;
                Thread vendorThread = new Thread(() -> {
                    while (running) {
                        vendors[finalI].run();
                    }
                }, "vendor-" + i);
                vendorThread.start();
            }

            // Create and start customer threads
            for (int i = 0; i < customers.length; i++) {
                customers[i] = new Customer(ticketPool, loadedConfig.getCustomerRetrievalRate(), loadedConfig.getTotalTickets());
                int finalI = i;
                Thread customerThread = new Thread(() -> {
                    while (running) {
                        customers[finalI].run();
                    }
                }, "customer-" + i);
                customerThread.start();
            }

            // Keep the simulation running until stopped
            while (running) {
                try {
                    Thread.sleep(1000); // Main thread sleeps to reduce CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        simulationThread.start();
        System.out.println("Simulation started.");
    }

    public static void stopSimulation() {
        if (simulationThread == null || !simulationThread.isAlive()) {
            System.out.println("No simulation is currently running.");
            return;
        }

        running = false; // Set the control flag to false
        try {
            simulationThread.join(); // Wait for the simulation thread to terminate
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Simulation has been stopped.");
    }

    // Method to validate positive integer input
    private static int validatePositiveInput(Scanner scanner) {
        int value = 0;
        while (value <= 0) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value <= 0) {
                    System.out.print("Please enter a positive number: ");
                }
            } else {
                System.out.print("Invalid input (You can't use letters or special characters). Please enter a positive number: ");
                scanner.next(); // Clear the invalid input
            }
        }
        return value;
    }
}
