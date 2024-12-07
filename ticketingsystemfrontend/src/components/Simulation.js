import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Config.css";
import "bootstrap/dist/css/bootstrap.min.css";

function Simulation() {
  const [logs, setLogs] = useState([]);
  const [isRunning, setIsRunning] = useState(false);

  const startSimulation = () => {
    const config = {
      totalTickets: 50,
      maxCapacity: 10,
      ticketReleaseRate: 2,
      customerRetrievalRate: 3,
    };

    axios.post("http://localhost:8080/api/simulation/start", config)
      .then(() => {
        setIsRunning(true);
      })
      .catch((error) => alert("Error starting simulation: " + error.message));
  };

  const stopSimulation = () => {
    axios.post("http://localhost:8080/api/simulation/stop")
      .then(() => {
        setIsRunning(false);
      })
      .catch((error) => alert("Error stopping simulation: " + error.message));
  };

  useEffect(() => {
    const interval = setInterval(() => {
      if (isRunning) {
        axios.get("http://localhost:8080/api/simulation/logs")
          .then((response) => setLogs(response.data))
          .catch((error) => console.error("Error fetching logs:", error));
      }
    }, 1000);

    return () => clearInterval(interval);
  }, [isRunning]);

  return (
    <div className="container2">
      <h2>Ticketing Simulation</h2>
      <div className="controls">
        <button onClick={startSimulation} className="btn btn-success me-2" disabled={isRunning}>
          Start Simulation
        </button>
        <button onClick={stopSimulation} className="btn btn-danger" disabled={!isRunning}>
          Stop Simulation
        </button>
      </div>
      <div className="logs">
        <h3>Logs</h3>
        <ul>
          {logs.map((log, index) => (
            <li key={index}>{log}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Simulation;
