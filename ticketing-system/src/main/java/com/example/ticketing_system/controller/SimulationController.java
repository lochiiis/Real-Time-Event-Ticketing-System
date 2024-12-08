package com.example.ticketing_system.controller;

import com.example.ticketing_system.cli.Configuration;
import com.example.ticketing_system.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin
public class SimulationController {

    @Autowired  //class class connect
    private SimulationService simulationService;

    @PostMapping("/start")
    public String startSimulation(@RequestBody Configuration config) {
        simulationService.startSimulation(config.getTotalTickets(), config.getTicketReleaseRate(),
                config.getCustomerRetrievalRate(), config.getMaxTicketCapacity());
        return "Simulation started!";
    }

    @PostMapping("/stop")
    public String stopSimulation() {
        simulationService.stopSimulation();
        return "Simulation stopped!";
    }

    @GetMapping("/logs")
    public List<String> getLogs() {
        return simulationService.getSimulationLogs();
    }
}
