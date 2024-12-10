//package com.example.ticketing_system.controller;
//
//import com.example.ticketing_system.model.TicketSales;
//import com.example.ticketing_system.repository.TicketSalesRepository;
//import com.example.ticketing_system.service.TicketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/ticketSales")
//public class TicketController {
//
//
//    @Autowired
//    private TicketService ticketService;
//
//    @GetMapping
//    public ResponseEntity<List<TicketSales>>getAllTickets() {
//        return new ResponseEntity<List<TicketSales>>(ticketService.getAllTickets(), HttpStatus.OK);
//    }
//
//
//}
