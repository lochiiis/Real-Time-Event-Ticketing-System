import './Config.css';
import 'bootstrap/dist/css/bootstrap.min.css';



function Configurations() {
  return (
    <div className="container mt-4">
      <div className="card shadow p-4">
        <h2 className="text-center mb-4">Configurations</h2>
        <form>
          <div className="mb-3">
            <label htmlFor="totalTickets" className="form-label">Total Tickets</label>
            <input type="number" id="totalTickets" className="form-control" placeholder="Enter total tickets" />
          </div>
          <div className="mb-3">
            <label htmlFor="ticketReleaseRate" className="form-label">Ticket Release Rate</label>
            <input type="number" id="ticketReleaseRate" className="form-control" placeholder="Tickets/sec" />
          </div>
          <div className="mb-3">
            <label htmlFor="customerRetrievalRate" className="form-label">Customer Retrieval Rate</label>
            <input type="number" id="customerRetrievalRate" className="form-control" placeholder="Tickets/sec" />
          </div>
          <div className="mb-3">
            <label htmlFor="maxTicketCapacity" className="form-label">Max Ticket Capacity</label>
            <input type="number" id="maxTicketCapacity" className="form-control" placeholder="Max tickets in the pool" />
          </div>
          <button type="submit" className="btn btn-primary w-100">Save Configurations</button>
        </form>  
       </div>   
    </div>
  );
}

export default Configurations;