import React, { useState, useEffect } from "react";
import "./Config.css";

function Simulation() {
  const [tickets, setTickets] = useState([]); // List of tickets
  const [logs, setLogs] = useState([]); // Log for events
  const [vendorInterval, setVendorInterval] = useState(null);
  const [customerInterval, setCustomerInterval] = useState(null);
  const maxTicketCapacity = 10; // Maximum capacity of ticket pool
  const totalTickets = 20; // Total tickets allowed in the system

  // Add tickets (Vendor Simulation)
  const addTickets = () => {
    if (tickets.length >= maxTicketCapacity) {
      logEvent("Vendor: Ticket pool at max capacity. Cannot add tickets.");
      return;
    }
    if (tickets.length + 1 <= totalTickets) {
      setTickets((prev) => [...prev, { id: Date.now() }]);
      logEvent("Vendor: Added a ticket.");
    } else {
      logEvent("Vendor: All tickets have been added. No more tickets to add.");
      stopSimulation();
    }
  };

  // Remove tickets (Customer Simulation)
  const removeTicket = () => {
    if (tickets.length === 0) {
      logEvent("Customer: No tickets available. Waiting...");
      return;
    }
    setTickets((prev) => prev.slice(1));
    logEvent("Customer: Purchased a ticket.");
  };

  // Log events
  const logEvent = (message) => {
    setLogs((prev) => [...prev, { message, timestamp: new Date().toLocaleTimeString() }]);
  };

  // Start Simulation
  const startSimulation = () => {
    logEvent("Simulation started.");
    setVendorInterval(setInterval(addTickets, 2000)); // Vendor adds tickets every 2 seconds
    setCustomerInterval(setInterval(removeTicket, 1500)); // Customer buys tickets every 1.5 seconds
  };

  // Stop Simulation
  const stopSimulation = () => {
    logEvent("Simulation stopped.");
    if (vendorInterval) clearInterval(vendorInterval);
    if (customerInterval) clearInterval(customerInterval);
    setVendorInterval(null);
    setCustomerInterval(null);
  };

  return (
    <div className="simulation-container">
      <h1>Ticket Pool Simulation</h1>
      <div className="status">
        <h2>Ticket Pool</h2>
        <p>Tickets in Pool: {tickets.length}</p>
        <p>Max Capacity: {maxTicketCapacity}</p>
        <p>Total Tickets Allowed: {totalTickets}</p>
      </div>
      <div className="controls">
        <button className="btn btn-success" onClick={startSimulation} disabled={vendorInterval || customerInterval}>
          Start Simulation
        </button>
        <button className="btn btn-danger" onClick={stopSimulation} disabled={!vendorInterval && !customerInterval}>
          Stop Simulation
        </button>
      </div>
      <div className="logs">
        <h2>Logs</h2>
        <ul>
          {logs.map((log, index) => (
            <li key={index}>
              {log.timestamp} - {log.message}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Simulation;
