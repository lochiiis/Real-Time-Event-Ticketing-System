package com.example.ticketing_system.controller;


import com.example.ticketing_system.cli.Configuration;
import com.example.ticketing_system.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
@CrossOrigin
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;


    // Load configuration and return to frontend
    @GetMapping("/load")
    public Configuration loadConfiguration() {
        return configurationService.loadConfiguration();
    }

    // Save configuration received from the frontend
    @PostMapping("/save")
    public String saveConfiguration(@RequestBody Configuration configuration) {
        configurationService.saveConfiguration(configuration);
        return "Configuration saved successfully!";
    }

}
