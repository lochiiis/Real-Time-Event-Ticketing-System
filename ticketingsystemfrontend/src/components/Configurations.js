import React, { useState } from "react";
import axios from "axios";
import "./Config.css";
import "bootstrap/dist/css/bootstrap.min.css";

function Configurations() {
  const [config, setConfig] = useState({
    totalTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
    maxTicketCapacity: "",
  });

  const [loadedConfig, setLoadedConfig] = useState(null);

  const handleChange = (e) => {
    setConfig({ ...config, [e.target.id]: e.target.value });
  };

  const loadConfiguration = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get("http://localhost:8080/api/config/load");
      setLoadedConfig(response.data); // Update loaded configuration
      setConfig(response.data); // Populate the form with loaded values
      alert("Configuration loaded successfully!");
    } catch (error) {
      alert("Error loading configuration: " + error.message);
    }
  };

  const saveConfiguration = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/config/save", config);
      alert("Configuration saved successfully!");
    } catch (error) {
      alert("Error saving configuration: " + error.message);
    }
  };

  return (
    <div className="container">
      <div className="card1">
        <h2>Configurations</h2>
        <form>
          <div className="inputs">
            <label className="form-label">Total Tickets</label>
            <input
              type="number"
              id="totalTickets"
              className="form-control"
              placeholder="Enter total tickets"
              value={config.totalTickets}
              onChange={handleChange}
            />
          </div>
          <div className="inputs">
            <label className="form-label">Ticket Release Rate</label>
            <input
              type="number"
              id="ticketReleaseRate"
              className="form-control"
              placeholder="Tickets/sec"
              value={config.ticketReleaseRate}
              onChange={handleChange}
            />
          </div>
          <div className="inputs">
            <label className="form-label">Customer Retrieval Rate</label>
            <input
              type="number"
              id="customerRetrievalRate"
              className="form-control"
              placeholder="Tickets/sec"
              value={config.customerRetrievalRate}
              onChange={handleChange}
            />
          </div>
          <div className="inputs">
            <label className="form-label">Max Ticket Capacity</label>
            <input
              type="number"
              id="maxTicketCapacity"
              className="form-control"
              placeholder="Max tickets in the pool"
              value={config.maxTicketCapacity}
              onChange={handleChange}
            />
          </div>
          <button onClick={loadConfiguration} className="btn btn-primary me-2">
            Load Configuration
          </button>
          <button onClick={saveConfiguration} className="btn btn-success">
            Save Configuration
          </button>
        </form>
      </div>


        <div className="card2">
          <h3>Simulation</h3>
        </div>
      
    </div>
  );
}

export default Configurations;
