package com.iit.RealtimeTicketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iit.RealtimeTicketing.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByIsSoldTrue();
    List<Ticket> findByIsSoldFalse();
}