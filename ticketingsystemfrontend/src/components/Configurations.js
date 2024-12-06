import './Config.css';
import 'bootstrap/dist/css/bootstrap.min.css';



function Configurations() {
  return (
    <div className="container">
      <div className="card">
        <h2>Configurations</h2>
        <form>
          <div className="inputs">
            <label className="form-label">Total Tickets</label>
            <input type="number" id="totalTickets" className="form-control" placeholder="Enter total tickets" />
          </div>
          <div className="inputs">
            <label className="form-label">Ticket Release Rate</label>
            <input type="number" id="ticketReleaseRate" className="form-control" placeholder="Tickets/sec" />
          </div>
          <div className="inputs">
            <label className="form-label">Customer Retrieval Rate</label>
            <input type="number" id="customerRetrievalRate" className="form-control" placeholder="Tickets/sec" />
          </div>
          <div className="inputs">
            <label className="form-label">Max Ticket Capacity</label>
            <input type="number" id="maxTicketCapacity" className="form-control" placeholder="Max tickets in the pool" />
          </div>
          <button type="submit" className="btn btn-primary w-100">Save Configurations</button>
        </form>  
       </div>   
    </div>
  );
}

export default Configurations;