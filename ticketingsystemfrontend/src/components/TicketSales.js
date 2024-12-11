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

  // Ticket stats state
  const [ticketStats, setTicketStats] = useState({
    totalTickets: 0,
    ticketsReleased: 0,
    ticketsBought: 0,
    ticketsRemaining: 0,
  });

  const [chartData, setChartData] = useState({
    labels: ["Total Tickets", "Tickets Released", "Tickets Bought", "Tickets Remaining"],
    datasets: [
      {
        label: "Ticket Sales Data",
        data: [0, 0, 0, 0], // Initial data
        backgroundColor: ["#A2D2FF","#57cc99", "#CDB4DB", "#FFAFCC"],
        borderColor: "rgba(219, 219, 255, 1)",
        borderWidth: 1,
        hoverOffset: 30,
      },
    ],
  });

  const handleChange = (e) => {
    setConfig({ ...config, [e.target.id]: e.target.value });
  };

  const startSimulation = async () => {
    //Validate input
    if(
      config.totalTickets <=0 ||
      config.ticketReleaseRate <=0 ||
      config.customerRetrievalRate <=0 ||
      config.maxTicketCapacity <=0
    )
    {
      alert("Please check the input values. They should be greater than 0");
      return;
    }
    
    

    try {
      setLogs([]); // Clear logs on the frontend
      setTicketStats({
        totalTickets: 0,
        ticketsReleased: 0,
        ticketsBought: 0,
        ticketsRemaining: 0,
      }); // Reset ticket stats
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

  const fetchLogsAndUpdateStats = async () => {
    if (!simulationRunning) return; // Exit if simulation is stopped

    try {
      // Fetch logs and analytics data (tickets released, bought, and remaining)
      const [logsResponse, analyticsResponse] = await Promise.all([
        axios.get("http://localhost:8080/api/simulation/logs"),
        axios.get("http://localhost:8080/api/simulation/analytics"),
      ]);

      // Update logs
      setLogs(logsResponse.data);

      const { totalTickets, ticketsReleased, ticketsBought, ticketsRemaining } = analyticsResponse.data;

      // Update ticket stats
      setTicketStats({ totalTickets, ticketsReleased, ticketsBought, ticketsRemaining });

      // Update chart data
      setChartData({
        labels: ["Total Tickets", "Tickets Released", "Tickets Bought", "Tickets Remaining"],
        datasets: [
          {
            label: "Ticket Sales Data",
            data: [totalTickets, ticketsReleased, ticketsBought, ticketsRemaining],
            backgroundColor: ["#A2D2FF","#57cc99", "#CDB4DB", "#FFAFCC"],
            borderColor: "rgba(75, 192, 192, 1)",
            borderWidth: 1,
            hoverOffset: 30,
          },
        ],
      });

      // Recursively call fetchLogsAndUpdateStats for long polling
      fetchLogsAndUpdateStats();
    } catch (error) {
      console.error("Error fetching logs or analytics:", error);
      setTimeout(fetchLogsAndUpdateStats, 2000); // Retry after 2 seconds in case of an error
    }
  };

  // Long polling - Initiates fetchLogsAndUpdateStats when simulation starts
  useEffect(() => {
    if (simulationRunning) {
      fetchLogsAndUpdateStats(); // Start long polling
    }
    return () => {
      setLogs([]); // Clear logs when simulation stops
    };
  }, [simulationRunning]);

  return (
    <div className="container">
      <div className="column1">
        <div className="card1">
          <h2>Configurations</h2>
          <form>
            <label htmlFor="totalTickets">Total Tickets</label>
            <input
              type="number"
              id="totalTickets"
              placeholder="Total Tickets"
              value={config.totalTickets}
              onChange={handleChange}
            />

            <label htmlFor="ticketReleaseRate">Ticket Release Rate</label>
            <input
              type="number"
              id="ticketReleaseRate"
              placeholder="Release Rate (Seconds)"
              value={config.ticketReleaseRate}
              onChange={handleChange}
            />

            <label htmlFor="customerRetrievalRate">Customer Retrieval Rate</label>
            <input
              type="number"
              id="customerRetrievalRate"
              placeholder="Retrieval Rate (Seconds)"
              value={config.customerRetrievalRate}
              onChange={handleChange}
            />

            <label htmlFor="maxTicketCapacity">Max Ticket Capacity</label>
            <input
              type="number"
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

        <div className="card2">
          <h2>Ticket Sales Analytics</h2>
          <div className="chart-container">
            <Pie data={chartData} />
          </div>
        </div>  

      </div>   

      <div className="column2">
        <div className="card3">
          <h2>Ticket Status</h2>
            <div className="ticket-stats">
              <div className="stat-item">
              <p className="stat-label">Total Tickets with the vendors</p>
                <p className="stat-number">{ticketStats.totalTickets}</p>
              </div>
              <div className="stat-item">
              <p className="stat-label">Tickets released by vendors</p>
              <p className="stat-number">{ticketStats.ticketsReleased}</p>
              </div>
              <div className="stat-item">
              <p className="stat-label">Tickets bought by customers</p>
              <p className="stat-number">{ticketStats.ticketsBought}</p>
              </div>
              <div className="stat-item">
              <p className="stat-label">Tickets remaining in the pool</p>
              <p className="stat-number">{ticketStats.ticketsRemaining}</p>
              </div>
            </div>
        </div>  


      

        <div className="card4">
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
  </div>    



  );
}

export default TicketSales;
