package com.dianant.reservationservice.models;

import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // not user provided, will be setup automatically at the time of reservation creation
    private Integer seatNumber;

    @NotNull(message = "Customer-Id cannot be blank")
    private Long customerId;

    @NotNull(message = "Flight-Id cannot be blank")
    private Long flightId;

    // not user provided, will be setup automatically in constructor
    private ZonedDateTime reservationZonedDateTime;

    // Not to be set by user
    private String reservationStatus;

    public Reservation(){
        this.reservationZonedDateTime = ZonedDateTime.now();
        this.reservationStatus = "SCHEDULED";
    }

    public Reservation(Long id, Integer seatNumber, Long customerId, Long flightId, ZonedDateTime reservationZonedDateTime, String reservationStatus) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.customerId = customerId;
        this.flightId = flightId;
        this.reservationZonedDateTime = ZonedDateTime.now();
        this.reservationStatus = "SCHEDULED";
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long passengerId) {
        this.customerId = passengerId;
    }
    public Long getFlightId() {
        return flightId;
    }
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public ZonedDateTime getReservationZonedDateTime() {
        return reservationZonedDateTime;
    }

    public void setReservationZonedDateTime(ZonedDateTime reservationZonedDateTime) {
        this.reservationZonedDateTime = reservationZonedDateTime;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
