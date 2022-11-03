package com.dianant.flightservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    FlightRepository flightRepository;
    @InjectMocks
    FlightService underTest;

    @Test
    void canGetAllFlights() {
        // given
        Flight flight = new Flight(1L, "Some Airline", "departure location", "arrival location",
                "2", ZonedDateTime.now(), new HashSet<>());

        List<Flight> allFlights = new ArrayList<>();
        allFlights.add(flight);
        // when
        Mockito.lenient().when(flightRepository.findAll()).thenReturn(allFlights);
        // then
        assertEquals(ResponseEntity.ok(allFlights), underTest.getAllFlights());
    }

    @Test
    void canCreateFlight() {
        // given
        Flight flight = new Flight(1L, "Some Airline", "departure location", "arrival location",
                "2", ZonedDateTime.now(), new HashSet<>());
        // when
        Mockito.lenient().when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        //then
        assertEquals(ResponseEntity.ok(flight), underTest.createFlight(flight));
    }

    @Test
    void getFlightById() {
        // given
        Flight flight = new Flight(1L, "Some Airline", "departure location", "arrival location",
                "2", ZonedDateTime.now(), new HashSet<>());
        // when
        Mockito.lenient().when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        //then
        assertEquals(ResponseEntity.ok(flight), underTest.getFlightById(1L));
    }

    @Test
    void canDeleteFlightById() {
        // given
        Flight flight = new Flight(1L, "Some Airline", "departure location", "arrival location",
                "2", ZonedDateTime.now(), new HashSet<>());
        // when
        Mockito.lenient().when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        Mockito.lenient().doNothing().when(flightRepository).delete(any(Flight.class));
        //then
        assertEquals(ResponseEntity.ok(flight), underTest.deleteFlight(1L));
    }

    @Test
    void bookASeat() {
        // given
        Flight flight = new Flight(1L, "Some Airline", "departure location", "arrival location",
                "2", ZonedDateTime.now(), new HashSet<>());
        // when
        Mockito.lenient().when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        //then
        assertEquals(ResponseEntity.ok(1), underTest.bookASeat(1L));
    }

    @Test
    void cancelASeat() {
        // given
        int seatNumberBookedInitially = 1;
        long flightId = 1L;
        Flight flight = new Flight(flightId, "Some Airline", "departure location", "arrival location",
                "2", ZonedDateTime.now(), new HashSet<>(List.of(seatNumberBookedInitially)));
        // when
        Mockito.lenient().when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        Mockito.lenient().when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        // then
        assertEquals(ResponseEntity.ok("Seat Number " + seatNumberBookedInitially + " successfully un-reserved on Flight Id " + flightId + "."),
                underTest.cancelASeat(flightId, seatNumberBookedInitially));
    }
}