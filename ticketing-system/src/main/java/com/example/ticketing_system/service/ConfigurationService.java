package com.example.ticketing_system.service;


import com.example.ticketing_system.cli.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ConfigurationService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Load configuration from the file
    public Configuration loadConfiguration() {
        try (FileReader reader = new FileReader("config.json")) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Return default configuration if the file is not found or cannot be read
            return new Configuration(100, 1, 1, 50); // Default values
        }
    }

    // Save configuration to the file
    public void saveConfiguration(Configuration configuration) {
        try (FileWriter writer = new FileWriter("config.json")) {
            gson.toJson(configuration, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
