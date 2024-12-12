package com.iit.RealtimeTicketing.controller;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iit.RealtimeTicketing.CLI.Main;
import com.iit.RealtimeTicketing.CLI.WebConfiguration;
import com.iit.RealtimeTicketing.model.Ticket;
import com.iit.RealtimeTicketing.service.CustomerService;
import com.iit.RealtimeTicketing.service.TicketPoolService;
import com.iit.RealtimeTicketing.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController {

    @Autowired
    private final TicketPoolService ticketPool;

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final VendorService vendorService;

    public TicketController(TicketPoolService ticketPool, CustomerService customerService, VendorService vendorService) {
        this.ticketPool = ticketPool;
        this.customerService = customerService;
        this.vendorService = vendorService;
    }


    @GetMapping("/buy")
    public Ticket buyTicket() {
        return ticketPool.buyTicket();
    }
    // Simulate a vendor adding tickets
    @PostMapping("/simulate-vendor")
    public String simulateVendor(@RequestParam int ticketsToProduce) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> vendorService.run());
        executor.shutdown();
        return "Vendor simulation started.";
    }

    // Simulate a customer buying tickets
    @PostMapping("/simulate-customer")
    public String simulateCustomer(@RequestParam int customersToSimulate) {
        ExecutorService executor = Executors.newFixedThreadPool(customersToSimulate);
        for (int i = 0; i < customersToSimulate; i++) {
            executor.submit(customerService);
        }
        executor.shutdown();
        return "Customer simulation started.";
    }
}
