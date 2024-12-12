package com.iit.RealtimeTicketing.CLI;


import com.iit.RealtimeTicketing.CLI.Ticket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Getter
//@Setter
@Component
public class TicketList {
    private static final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private static int totalTickets;
    private static int availableTickets;
    private static int soldTickets;

    public static void addTicket(Ticket ticket) {
        tickets.add(ticket);
        totalTickets++;
        availableTickets++;
    }

    public static Ticket removeTicket() {
        availableTickets--;
        soldTickets++;
        return tickets.removeFirst();
    }

    public static List<Ticket> getTickets() {
        return tickets;
    }

    public static int getTotalTickets() {
        return totalTickets;
    }

    public static int getAvailableTickets() {
        return availableTickets;
    }

    public static int getSoldTickets() {
        return soldTickets;
    }
}
