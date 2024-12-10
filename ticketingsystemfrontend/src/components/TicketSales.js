import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Config.css";
import { Pie } from "react-chartjs-2";
import "chart.js/auto";

function TicketSales() {
  const [config, setConfig] = useState({
    totalTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
    maxTicketCapacity: "",
  });

  const [logs, setLogs] = useState([]);
  const [simulationRunning, setSimulationRunning] = useState(false); // Track simulation status

  const [chartData, setChartData] = useState({
    labels: ["Total Tickets","Tickets Released", "Tickets Bought", "Tickets Remaining"],
    datasets: [
      {
        label: "Ticket Sales Data",
        data: [0,0, 0, 0], // Initial data
        backgroundColor: ["#21caf2","#4caf50", "#f44336", "#2196f3"],
        borderColor: "rgba(75, 192, 192, 1)",
        borderWidth: 1,
        hoverOffset: 30,
      },
    ],
  });

  const handleChange = (e) => {
    setConfig({ ...config, [e.target.id]: e.target.value });
  };

  const startSimulation = async () => {
    try {
      setLogs([]); // Clear logs on the frontend
      await axios.post("http://localhost:8080/api/simulation/start", config);
      setSimulationRunning(true); // Set simulation running state to true
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

  const fetchLogsAndUpdateChart = async () => {
    if (!simulationRunning) return; // Exit if simulation is stopped

    try {
      // Fetch logs and analytics data (tickets released, bought, and remaining)
      const [logsResponse, analyticsResponse] = await Promise.all([
        axios.get("http://localhost:8080/api/simulation/logs"),
        axios.get("http://localhost:8080/api/simulation/analytics"),
      ]);

      // Update logs
      setLogs(logsResponse.data);

      const { totalTickets,ticketsReleased, ticketsBought, ticketsRemaining } = analyticsResponse.data;

      // Update chart data
      setChartData({
        labels: ["Total Tickets","Tickets Released", "Tickets Bought", "Tickets Remaining"],
        datasets: [
          {
            label: "Ticket Sales Data",
            data: [totalTickets,ticketsReleased, ticketsBought, ticketsRemaining],
            backgroundColor: ["21caf2","#4caf50", "#f44336", "#2196f3"],
            borderColor: "rgba(75, 192, 192, 1)",
            borderWidth: 1,
            hoverOffset: 30,
          },
        ],
      });

      // Recursively call fetchLogsAndUpdateChart for long polling
      fetchLogsAndUpdateChart();
    } catch (error) {
      console.error("Error fetching logs or analytics:", error);
      setTimeout(fetchLogsAndUpdateChart, 2000); // Retry after 2 seconds in case of an error
    }
  };

  // Long polling - Initiates fetchLogsAndUpdateChart when simulation starts
  useEffect(() => {
    if (simulationRunning) {
      fetchLogsAndUpdateChart(); // Start long polling
    }
    return () => {
      setLogs([]); // Clear logs when simulation stops
    };
  }, [simulationRunning]);

  return (
    <div className="container">
      <div className="card1">
        <h2>Configurations</h2>
        <form>
          <input
            type="text"
            id="totalTickets"
            placeholder="Total Tickets"
            value={config.totalTickets}
            onChange={handleChange}
          />

          <input
            type="text"
            id="ticketReleaseRate"
            placeholder="Release Rate"
            value={config.ticketReleaseRate}
            onChange={handleChange}
          />

          <input
            type="text"
            id="customerRetrievalRate"
            placeholder="Retrieval Rate"
            value={config.customerRetrievalRate}
            onChange={handleChange}
          />

          <input
            type="text"
            id="maxTicketCapacity"
            placeholder="Max Capacity"
            value={config.maxTicketCapacity}
            onChange={handleChange}
          />

          <button type="button" onClick={startSimulation}>
            Start Simulation
          </button>

          <button type="button" onClick={stopSimulation}>
            Stop Simulation
          </button>
        </form>
      </div>

      <div>
        

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

      <div className="chart-container">
        <h2>Ticket Sales Analytics</h2>
        <Pie data={chartData} />
      </div>
      
    </div>
  );
}

export default TicketSales;
