package com.iit.RealtimeTicketing.service;


import com.iit.RealtimeTicketing.CLI.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimService {
    private boolean running = false;
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();

    public synchronized String startSimulation() {
        if (running) {
            return "Simulation is already running.";
        }

        WebConfiguration loadedConfig = WebConfiguration.loadFromFile("config.json");
        if (loadedConfig == null || !loadedConfig.validate()) {
            return "Invalid configuration. Please ensure the config.json file is correctly set up.";
        }



        running = true;

        Thread simulationThread = new Thread(() -> {
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

            //System.out.println("Simulation stopped.");
        });

        simulationThread.start();

        return "Simulation started successfully.";
    }

    public synchronized String stopSimulation() {
        if (!running) {
            return "Simulation is not running.";
        }

        // Interrupt all threads
        vendorThreads.forEach(Thread::interrupt);
        customerThreads.forEach(Thread::interrupt);
        vendorThreads.clear();
        customerThreads.clear();

        running = false;
        return "Simulation stopped successfully.";
    }

    public boolean isRunning() {
        return running;
    }
}
