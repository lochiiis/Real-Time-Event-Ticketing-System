package com.example.ticketing_system.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class TicketSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TransactionId;

    private Integer ticketId;
    private String eventName;
    private Double ticketPrice;
    private Integer customerId;

    public TicketSales() {
    }

    public TicketSales(Integer ticketId, String eventName, Double ticketPrice, Integer customerId) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.customerId = customerId;
    }


}
