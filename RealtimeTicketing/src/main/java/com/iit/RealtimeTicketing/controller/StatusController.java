package com.iit.RealtimeTicketing.controller;

import com.iit.RealtimeTicketing.CLI.TicketList;
import com.iit.RealtimeTicketing.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class StatusController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/api/ticket/status")
    public Map<String, Integer> getTicketStatus() {
        int totalTickets = TicketList.getTotalTickets();
        int soldTickets = TicketList.getSoldTickets();
        int availableTickets = TicketList.getAvailableTickets();

        Map<String, Integer> status = new HashMap<>();
        status.put("totalTickets", totalTickets);
        status.put("soldTickets", soldTickets);
        status.put("availableTickets", availableTickets);

        return status;
    }
}