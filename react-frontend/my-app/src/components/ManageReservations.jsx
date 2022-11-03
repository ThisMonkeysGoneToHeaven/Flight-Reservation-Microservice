import React from 'react'
import ReservationService from '../services/ReservationService';
import { useEffect, useState } from 'react';
import FlightService from '../services/FlightService';
import { useNavigate } from 'react-router-dom';

export default function ManageReservations(props) {
  
  const customerId = props.useCustomerId;
  const [reservationData, setReservationData] = useState(null);
  const [allFlightsData, setAllFlightsData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    ReservationService.getReservationsByCustomerId(customerId).then((res) => {
      setReservationData(res.data);
    });

    FlightService.getAllFlights().then((res) => {
      setAllFlightsData(res.data);
    });
  }, [customerId]);

  const handleCancelReservation = (reservationId) => {
    ReservationService.deleteReservation(reservationId).then((res) => {
      navigate("/customer-homepage");
    });
  };

  return (
          <div>
          {
            (reservationData==null || allFlightsData==null) ? <h2 className='text-center'>Please Wait While We Load Your Details!</h2> :
            reservationData!=null &&
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
                                  <th>Seat Number</th>
                                  <th>Actions</th>
                              </tr>
                          </thead>
                          <tbody>
                              {
                                  reservationData.map(function(reservation){
                                    const  flightDetails = allFlightsData.find((flight) => flight.id === reservation.flightId);
                                    return <tr key={reservation.id}>
                                          <td>{reservation.id}</td>
                                          <td>{flightDetails.airlineName}</td>
                                          <td>{flightDetails.toLocation}</td>
                                          <td>{flightDetails.fromLocation}</td>
                                          <td>{flightDetails.dateTimeZone.slice(0,10).replaceAll('-','/')}</td>
                                          <td>{flightDetails.dateTimeZone.slice(11, 16)}</td>
                                          <td>{flightDetails.gateNumber}</td>
                                          <td>{reservation.seatNumber}</td>
                                          <td>
                                              <button className="btn btn-danger btn-sm mx-3" onClick={() => handleCancelReservation(reservation.id)}>CANCEL</button>
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
