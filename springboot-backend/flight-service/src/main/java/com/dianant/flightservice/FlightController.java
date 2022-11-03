package com.dianant.flightservice;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return flightService.getAllFlights();
    }

    @PostMapping(value = "/")
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable Long id) {
        return flightService.deleteFlight(id);
    }

    @GetMapping(value = "/book/{id}")
    public ResponseEntity<Integer> bookASeat(@PathVariable Long id) {
        return flightService.bookASeat(id);
    }

    @GetMapping(value = "/cancel/{flightId}")
    public  ResponseEntity<String> cancelASeat(@PathVariable Long flightId, @RequestParam Integer seatNumber){
        return flightService.cancelASeat(flightId, seatNumber);
    };
}