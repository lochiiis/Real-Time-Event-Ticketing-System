package com.example.ticketing_system.service;


import com.example.ticketing_system.model.Ticket;
import com.example.ticketing_system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {


    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public Ticket addTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    /**
     *
     * Optional is used to avoid null values and prevent NullPointerException. It represents a value that might be present or absent.
     * Example:
     * Present: If a value exists, Optional lets you access it safely.
     * Absent: If no value exists, Optional avoids errors by providing alternatives (e.g., default values).
     *
     */

    @Override
    public Optional<Ticket> sellTicket(int ticketId){
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isPresent()){
            Ticket ticketSold = ticket.get();
            ticketSold.setSold(true);
            ticketRepository.save(ticketSold);
        }
        return ticket;
    }

    public List<Ticket> getAvailableTickets(){
        return ticketRepository.findAll();
    }

    public long getTicketCount(){
        return ticketRepository.count();
    }

}
