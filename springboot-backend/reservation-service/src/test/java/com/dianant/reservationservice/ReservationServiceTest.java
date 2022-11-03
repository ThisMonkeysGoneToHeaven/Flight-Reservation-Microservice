package com.dianant.reservationservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;
    @InjectMocks
    ReservationService reservationService;

    @Test
    void getAllReservations() {
    }

    @Test
    void createReservation() {
    }

    @Test
    void getReservationsByCustomerId() {
    }

    @Test
    void getReservationsByFlightId() {
    }

    @Test
    void getReservationById() {
    }

    @Test
    void deleteReservation() {
    }

    @Test
    void testGetAllReservations() {
    }

    @Test
    void testCreateReservation() {
    }

    @Test
    void testGetReservationsByCustomerId() {
    }

    @Test
    void testGetReservationsByFlightId() {
    }

    @Test
    void testGetReservationById() {
    }

    @Test
    void testDeleteReservation() {
    }

    @Test
    void deleteAllReservationsByCustomerId() {
    }
}