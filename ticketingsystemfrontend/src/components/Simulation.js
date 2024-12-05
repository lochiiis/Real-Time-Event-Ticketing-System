import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Config.css";

const Simulation = () => {
  
    const [config,setConfig] =useState({});
    const [tickets, setTickets] = useState([]);
    const [logs, setLogs] = useState([]);
    const [running, setRunning] = useState(false);
    const [vendorInterval, setVendorInterval] = useState(null);
    const [customerInterval, setCustomerInterval] = useState(null);


    //load initial configuration form backend
    useEffect(() => {
        axios.get("/api/config") //update endpoint to match backend
        .then((response) => setConfig(response.data))
        .catch((err) => console.error("Error fetching configurations :", err));
    } , []);

    //vendor add tickets
    const vendorAction = () =>{
        axios.post("/api/tickets/addTickets",{ticketId : Date.now(), eventName : "Spandana"})
        .then(() => {
            

        })
        .catch((err) => console.error("Error adding tickets :", err));
    };









}

export default Simulation;
