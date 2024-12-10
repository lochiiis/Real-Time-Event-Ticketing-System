package com.example.ticketing_system.repository;

import com.example.ticketing_system.model.TicketSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSalesRepository extends JpaRepository<TicketSales, Integer> {

}
