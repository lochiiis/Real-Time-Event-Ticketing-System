package com.example.ticketing_system.controller;

import com.example.ticketing_system.cli.Configuration;
import com.example.ticketing_system.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin //Allows cross-origin requests from different domains
public class SimulationController {

    @Autowired  // Injects the SimulationService for handling business logic
    private SimulationService simulationService;

    //starts the simulation with the provided configuration settings
    @PostMapping("/start")
    public String startSimulation(@RequestBody Configuration config) {
        simulationService.startSimulation(
                config.getTotalTickets(),
                config.getTicketReleaseRate(),
                config.getCustomerRetrievalRate(),
                config.getMaxTicketCapacity()
        );

        return "Simulation started!";
    }

    //stops the currently running simulation
    @PostMapping("/stop")
    public String stopSimulation() {
        simulationService.stopSimulation();
        return "Simulation stopped!";
    }

    //get logs from the simulation
    @GetMapping("/logs")
    public List<String> getLogs() {
        return simulationService.getSimulationLogs();
    }

    //retrieves real time analytics data for the ticketing simulation
    @GetMapping("/analytics")
    public Map<String,Integer> getAnalytics() {
        return simulationService.getAnalytics();
    }

}
