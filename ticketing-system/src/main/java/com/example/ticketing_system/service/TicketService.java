package com.example.ticketing_system.service;

import com.example.ticketing_system.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Ticket addTicket(Ticket ticket);

    Optional<Ticket> sellTicket(int ticketId);

    List<Ticket> getAvailableTickets();

    long getTicketCount();

}
