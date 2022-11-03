package com.dianant.flightservice;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightRepository.findAll());
    }

    public ResponseEntity<Flight> createFlight(Flight flight) {

        // we need to make sure that flightBoarding time is not of past
        Flight savedFlight = flightRepository.save(flight);
        return ResponseEntity.ok(savedFlight);
    }

    public ResponseEntity<Flight> getFlightById(Long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with id: " + id + " exists in the database!"));
        return ResponseEntity.ok(flight);
    }

    public ResponseEntity<Flight> deleteFlight(Long id) {

        Flight flight = flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No flight with id: \" + id + \" exists in the database."));

        flightRepository.delete(flight);
        return ResponseEntity.ok(flight);
    }

    public ResponseEntity<Integer> bookASeat(Long flightId) {

        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Flight with FlightId " + flightId + " found in the database!"));

        HashSet<Integer> bookedSeatsForThisFlight = flight.getBookedSeats();

        if(bookedSeatsForThisFlight.size() == Flight.INITIAL_SEAT_CAPACITY)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "All Seats On this Flight Are Reserved! Please find a different flight.");

        int bookedSeatNumber = 1;
        for(int i = 1; i <= Flight.INITIAL_SEAT_CAPACITY; i++){
            if(!bookedSeatsForThisFlight.contains(i)){
                bookedSeatsForThisFlight.add(i);
                bookedSeatNumber = i;
                break;
            }
        }

        flight.setBookedSeats(bookedSeatsForThisFlight);
        flightRepository.save(flight);
        return ResponseEntity.ok(bookedSeatNumber);
    }

    public ResponseEntity<String> cancelASeat(Long flightId, Integer seatNumber){

        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Flight with Flight-Id " + flightId + " found in the database! Please enter a correct flight id."));

        HashSet<Integer> bookedSeatsForThisFlight = flight.getBookedSeats();

        if(!bookedSeatsForThisFlight.contains(seatNumber))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat Number " + seatNumber + " is already un-reserved on Flight Id " + flightId + ". Please enter a correct seat number to un-reserve a seat on this flight.");

        bookedSeatsForThisFlight.remove(seatNumber);
        flight.setBookedSeats(bookedSeatsForThisFlight);
        flightRepository.save(flight);
        return ResponseEntity.ok("Seat Number " + seatNumber + " successfully un-reserved on Flight Id " + flightId + ".");
    }
}