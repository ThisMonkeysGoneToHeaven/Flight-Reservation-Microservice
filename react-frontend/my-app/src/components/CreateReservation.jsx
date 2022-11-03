import React, {useState} from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import FlightService from '../services/FlightService';
import ReservationService from '../services/ReservationService';

export default function CreateReservation(props) {

  const customerId = props.useCustomerId;
  const [allFlightsData, setAllFlightsData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    FlightService.getAllFlights().then((res) => {
      setAllFlightsData(res.data);
    });
  }, []);

  const handleCreateReservation = function(flightId){
    ReservationService.addReservation(customerId, flightId);
    navigate('/customer-homepage');
  }

  return (
    <div>
      {
        allFlightsData==null ? <h2 className='text-center'>Please Wait While We Load Your Details!</h2> :
        <div className='mb-5'>
        <h2 className="text-center pt-3 m-5">Your Reservations</h2>
        <div className="row">
            <table className="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Airline</th>
                        <th>Boarding Airport</th>
                        <th>Destination Airport</th>
                        <th>Flight Date</th>
                        <th>Flight Time</th>
                        <th>Gate</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        allFlightsData.map(function(flight){

                          return <tr key={flight.id}>
                                <td>{flight.id}</td>
                                <td>{flight.airlineName}</td>
                                <td>{flight.toLocation}</td>
                                <td>{flight.fromLocation}</td>
                                <td>{flight.dateTimeZone.slice(0,10).replaceAll('-','/')}</td>
                                <td>{flight.dateTimeZone.slice(11, 16)}</td>
                                <td>{flight.gateNumber}</td>
                                <td>
                                    <button className="btn btn-primary btn-sm mx-3" onClick={() => handleCreateReservation(flight.id)}>BOOK</button>
                                </td>
                            </tr>                                  
                        })
                    }
                </tbody>
            </table>
        </div>
        </div>
      }
    </div>
  )
}
