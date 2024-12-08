import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Config.css";

function Configurations() {
  const [config, setConfig] = useState({
    totalTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
    maxTicketCapacity: "",
  });

  const [logs, setLogs] = useState([]);
  const [simulationRunning, setSimulationRunning] = useState(false); // Track simulation status

  const handleChange = (e) => {
    setConfig({ ...config, [e.target.id]: e.target.value });
  };

  const startSimulation = async () => {
    try {
      setLogs([]); // Clear logs on the frontend
      await axios.post("http://localhost:8080/api/simulation/start", config);
      setSimulationRunning(true); // Set simulation running state to true
      // alert("Simulation started!");
    } catch (error) {
      alert("Error starting simulation: " + error.message);
    }
  };

  const stopSimulation = async () => {
    try {
      await axios.post("http://localhost:8080/api/simulation/stop");
      setSimulationRunning(false); // Set simulation running state to false
      alert("Simulation stopped!");
    } catch (error) {
      alert("Error stopping simulation: " + error.message);
    }
  };

  // Poll logs only if simulation is running
  useEffect(() => {
    if (!simulationRunning) return; // Only poll when simulation is running
    const interval = setInterval(() => {
      axios
        .get("http://localhost:8080/api/simulation/logs")
        .then((response) => setLogs(response.data))
        .catch((error) => console.error("Error fetching logs:", error));
    }, 2000);

    return () => clearInterval(interval); // Cleanup interval on component unmount or when simulation stops
  }, [simulationRunning]); // Re-run this effect when simulationRunning changes


  return (
    <div className="container">
      <div className="card1">
        <h2>Configurations</h2>
        <form>
          <input
            id="totalTickets"
            value={config.totalTickets}
            onChange={handleChange}
            placeholder="Total Tickets"
          />
          <input
            id="ticketReleaseRate"
            value={config.ticketReleaseRate}
            onChange={handleChange}
            placeholder="Release Rate"
          />
          <input
            id="customerRetrievalRate"
            value={config.customerRetrievalRate}
            onChange={handleChange}
            placeholder="Retrieval Rate"
          />
          <input
            id="maxTicketCapacity"
            value={config.maxTicketCapacity}
            onChange={handleChange}
            placeholder="Max Capacity"
          />
          <button type="button" onClick={startSimulation}>
            Start Simulation
          </button>
          <button type="button" onClick={stopSimulation}>
            Stop Simulation
          </button>
        </form>
      </div>

      <div className="card2">
        <h2>Simulation Logs</h2>
        <div className="logs">
        <ul>
          {logs.length > 0 ? (
            logs.map((log, index) => <li key={index}>{log}</li>)
          ) : (
            <li>No logs available. Start the simulation to view logs.</li>
          )}
        </ul>
        </div>
      </div>
    </div>
  );

  
}

export default Configurations;
