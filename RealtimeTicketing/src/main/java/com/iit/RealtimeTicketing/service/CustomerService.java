package com.iit.RealtimeTicketing.service;


import com.iit.RealtimeTicketing.logger.SystemLogger;
import com.iit.RealtimeTicketing.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements Runnable {
    @Autowired
    private TicketPoolService ticketPool;

    @Override
    public void run() {
        try {
            Ticket ticket = ticketPool.buyTicket();
            SystemLogger.logInfo(Thread.currentThread().getName() +
                    " purchased " + ticket.getTicketName());
        } catch (IllegalStateException e) {
            SystemLogger.logWarning("Purchase failed: " + e.getMessage());
        }
    }
}
