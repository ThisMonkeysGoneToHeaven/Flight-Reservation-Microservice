package com.dianant.reservationservice;


import com.dianant.reservationservice.models.Reservation;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        return reservationService.getAllReservations();
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> createReservation(@Valid @RequestBody Reservation reservation){
        return reservationService.createReservation(reservation);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id){
        return reservationService.getReservationById(id);
    }

    @GetMapping(value = "/customer/{customerId}")
    public ResponseEntity<List<Reservation>> getReservationsByCustomerId(@PathVariable Long customerId){
        return reservationService.getReservationsByCustomerId(customerId);
    }

    // delete by using reservationId
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable Long id){
        return reservationService.deleteReservation(id);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Boolean> deleteAllReservationsByCustomerId(@PathVariable Long customerId){
        return reservationService.deleteAllReservationsByCustomerId(customerId);
    }
}
