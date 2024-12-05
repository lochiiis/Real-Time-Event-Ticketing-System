package com.example.ticketing_system.repository;

import com.example.ticketing_system.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TicketRepository extends JpaRepository<Ticket, Integer> {}
