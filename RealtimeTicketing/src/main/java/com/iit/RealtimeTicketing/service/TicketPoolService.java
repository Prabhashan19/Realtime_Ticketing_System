package com.iit.RealtimeTicketing.service;


import com.iit.RealtimeTicketing.logger.SystemLogger;
import com.iit.RealtimeTicketing.model.Ticket;
import com.iit.RealtimeTicketing.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class TicketPoolService {
    @Autowired
    private TicketRepository ticketRepository;

    private final Object lock = new Object();

    public void addTicket(Ticket ticket) {
        synchronized (lock) {
            ticketRepository.save(ticket);
            SystemLogger.logInfo("Added ticket: " + ticket.getTicketName());
        }
    }

    public Ticket buyTicket() {
        synchronized (lock) {
            List<Ticket> availableTickets = ticketRepository.findByIsSoldFalse();
            if (availableTickets.isEmpty()) {
                SystemLogger.logWarning("No tickets available for purchase.");
                throw new IllegalStateException("No tickets available.");
            }

            Ticket ticket = availableTickets.get(0);
            ticket.setSold(true);
            ticketRepository.save(ticket);
            SystemLogger.logInfo("Purchased ticket: " + ticket.getTicketName());
            return ticket;
        }
    }
}
