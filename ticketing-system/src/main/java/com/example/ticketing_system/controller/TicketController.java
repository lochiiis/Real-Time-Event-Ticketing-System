package com.example.ticketing_system.controller;


import com.example.ticketing_system.model.Ticket;
import com.example.ticketing_system.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }



    @PostMapping("/addTickets")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.addTicket(ticket);
    }


    @PostMapping("/sellTicket/{id}")
    public String sellTicket(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.sellTicket(id);
        if (ticket.isPresent()) {
            return "Ticket sold successfully";
        }else{
            return "Ticket not found!";
        }
    }


    @GetMapping("/tickets")
    public List<Ticket> getAvailableTickets() {
        return ticketService.getAvailableTickets();
    }


    @GetMapping("/ticketCount")
    public long getTicketCount() {
        return ticketService.getTicketCount();
    }


}
