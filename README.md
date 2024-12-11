# Real-Time Event Ticketing Simulation System

## Overview

This project is a **real-time event ticketing system** designed to simulate the ticketing process using the **Producer-Consumer pattern**. Developed as part of an Object-Oriented Programming (OOP) coursework, the system offers real-time updates and synchronization across the **frontend**, **backend**, and **Command-Line Interface (CLI)** components.

## Features

- **Real-Time Updates**: Displays real-time ticket availability and booking status.
- **Producer-Consumer Pattern**: Simulates vendors producing tickets and customers consuming them.
- **Multi-Threading**: Backend uses synchronized threads for managing ticket pools.
- **Frontend Application**: User-friendly React interface for ticket management.
- **REST API Integration**: Developed with Spring Boot for seamless frontend-backend communication.
- **CLI for Simulation**: Command-line interface for testing ticketing operations.

## Technologies Used

### Backend
- **Java**: Core application logic and thread management.
- **Spring Boot**: REST API development and application control.
- **Maven**: Build and dependency management.

### Frontend
- **React**: Interactive and dynamic user interface.
- **CSS**: Styling and responsiveness.
- **JavaScript**: Functional programming for frontend components.

### Other
- **JSON**: Configuration and data management.
- **Multi-Threading**: Synchronized operations for ticket handling.

## Project Structure

```
project-root/
|— backend/
|   |— src/main/java/com/example/ticketing_system/
|       |— cli/
|       |   |— Configuration.java
|       |   |— Customer.java
|       |   |— TicketPool.java
|       |   |— Vendor.java
|       |— controller/
|       |   |— SimulationController.java
|       |— service/
|           |— SimulationService.java
|           |— RealTimeTicketingSystemApplication.java
|   |— resources/
|   |— test/
|   |— pom.xml
    |— config.json
    |— TicketingSystemLogs.txt
|
|— frontend/
    |— src/
        |— components/
        |   |— Config.css
        |   |— TicketSales.js
        |— App.js
        |— index.js
    |— public/
    |— package.json
```

## Setup Instructions


### Frontend Setup
1. Navigate to the `frontend/` directory.
2. Start the React development server:
   ```bash
   npm start
   ```

### CLI Setup
1. Navigate to the backend project.
2. Compile the CLI application
3. Run the CLI application


## Usage

1. Start the **backend server**.
2. Launch the **frontend React application**.
3. Optionally, use the **CLI** to simulate ticket production and consumption processes.
4. Use the frontend interface to view ticket availability in real-time and perform booking operations.

## Key OOP Concepts

- **Encapsulation**: Separation of ticket operations and data handling into modular components.
- **Polymorphism**: Unified handling of producers and consumers in the system.
- **Inheritance**: Reuse of shared logic among related classes.
- **Abstraction**: Clear and structured interfaces for ticket management.


---

Feel free to reach out with questions, suggestions, or feedback!
