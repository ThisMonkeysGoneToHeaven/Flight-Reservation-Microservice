package com.dianant.reservationservice;

import com.dianant.reservationservice.models.Reservation;
import com.dianant.reservationservice.models.Customer;
import com.dianant.reservationservice.models.Flight;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    // use rest-template to make an API call to customer and flight microservices to get both objects by their respective id's
    @Autowired
    private RestTemplate restTemplate;

    // getting the logger for doing logging operations down below
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }

    public ResponseEntity<String> createReservation(Reservation reservation) {

        String customerByIdURL = "http://customer-microservice/api/customers/" + reservation.getCustomerId();
        String flightByIdURL = "http://flight-microservice/api/flights/" + reservation.getFlightId();
        Customer customer = restTemplate.getForObject(customerByIdURL, Customer.class);
        Flight flight = restTemplate.getForObject(flightByIdURL, Flight.class);

        // checking if any of the above fetched objects could be null or not. This step can probably be removed because I expect the other microservices to generate the exception, but I'll keep this in place for now.
        if(flight == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Flight with flight id: " + reservation.getFlightId() + " found in the database!");
        }
        if(customer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer with customer id: " + reservation.getCustomerId() + " found in the database!");
        }

        // checking if we have any available seats on this flight
        if(flight.getBookedSeats().size() == Flight.INITIAL_SEAT_CAPACITY){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "All seats of this flight are booked. Please find a different flight!");
        }

        // booking a seat for the customer and getting and setting the seatNumber in this reservation object
        String bookSeatURL = "http://flight-microservice/api/flights/book/" + reservation.getFlightId();
        Integer seatNumber = restTemplate.getForObject(bookSeatURL ,Integer.class);
        reservation.setSeatNumber(seatNumber);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ResponseEntity.ok("Thanks " + customer.getFirstName() + " for using our services. Your Flight is successfully booked. Your reservation id is " + savedReservation.getId() + " and seat number is " + seatNumber + " on flight-id " + reservation.getFlightId());
    }

    // need to check if this is working properly or not
    public ResponseEntity<List<Reservation>> getReservationsByCustomerId(Long customerId) {
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        return ResponseEntity.ok(reservations);
    }

    // need to check if this is working properly or not
    public ResponseEntity<List<Reservation>> getReservationsByFlightId(Long flightId) {
        List<Reservation> reservations = reservationRepository.findByFlightId(flightId);
        return ResponseEntity.ok(reservations);
    }

    public  ResponseEntity<Reservation> getReservationById(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No reservation found by reservation id: " + reservationId));

        return ResponseEntity.ok(reservation);
    }

    public ResponseEntity<Boolean> deleteReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Reservation found by reservation id: " + reservationId + ". Please enter a valid reservation id!"));

        // need to un-reserve the previously booked seat too
        String seatCancelURL = "http://flight-microservice/api/flights/cancel/" + reservation.getFlightId() + "?seatNumber=" + reservation.getSeatNumber();
        String response = restTemplate.getForObject(seatCancelURL, String.class);
        logger.info(response);

        reservationRepository.delete(reservation);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> deleteAllReservationsByCustomerId(Long customerId) {

        List<Reservation> allReservationsOfThisCustomer = getReservationsByCustomerId(customerId).getBody();

        if(allReservationsOfThisCustomer != null){
            for(Reservation reservation: allReservationsOfThisCustomer){

                // need to un-reserve the previously booked seat too
                String seatCancelURL = "http://flight-microservice/api/flights/cancel/" + reservation.getFlightId() + "?seatNumber=" + reservation.getSeatNumber();
                String response = restTemplate.getForObject(seatCancelURL, String.class);
                logger.info(response);

                reservationRepository.delete(reservation);
            }
        }

        return ResponseEntity.ok(true);
    }
}
