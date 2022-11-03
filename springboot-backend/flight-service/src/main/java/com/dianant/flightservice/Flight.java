package com.dianant.flightservice;

import java.time.ZonedDateTime;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Airline Name cannot be blank!")
    private String airlineName;

    @NotBlank(message = "From-Location cannot be blank!")
    private String fromLocation;

    @NotBlank(message = "To-Location cannot be blank!")
    private String toLocation;

    @NotBlank(message = "Airport Gate Number cannot be blank")
    private String gateNumber;

    // need to create a custom validator for checking that time/date/zone is correctly formatted. Also need to make sure that date/time is not past and has at least 4-hour difference from current time.
    // @NotBlank(message = "Boarding time cannot be blank")
    private ZonedDateTime dateTimeZone;

    public static final int INITIAL_SEAT_CAPACITY = 100;

    // this bookedSeats list will have all the seats from 1 to 100 (inclusive) which have been reserved
    private HashSet<Integer> bookedSeats;


    public Flight(Long id, String airlineName, String fromLocation, String toLocation, String gateNumber, ZonedDateTime dateTimeZone, HashSet<Integer> bookedSeats) {
        this.id = id;
        this.airlineName = airlineName;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.gateNumber = gateNumber;
        this.dateTimeZone = dateTimeZone;
        this.bookedSeats = bookedSeats;
    }

    public Flight(){

        // initially this is going to be empty
        this.bookedSeats = new HashSet<>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAirlineName() {
        return airlineName;
    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    public String getFromLocation() {
        return fromLocation;
    }
    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }
    public String getToLocation() {
        return toLocation;
    }
    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }
    public String getGateNumber() {
        return gateNumber;
    }
    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }
    public ZonedDateTime getDateTimeZone() {
        return dateTimeZone;
    }
    public void setDateTimeZone(ZonedDateTime dateTimeZone) {
        this.dateTimeZone = dateTimeZone;
    }

    public HashSet<Integer> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(HashSet<Integer> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }
}