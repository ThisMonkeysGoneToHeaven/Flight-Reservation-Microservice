import axios from 'axios';

const RESERVATION_API_BASE_URL = "http://localhost:9004/api/reservations";

const ReservationService = {

    // getAllReservations: function(){
    //     return axios.get(RESERVATION_API_BASE_URL);
    // },

    getReservationById: function(reservationId){
        return axios.get(RESERVATION_API_BASE_URL + '/' + reservationId);
    },

    getReservationsByCustomerId: function(customerId){
        return axios.get(RESERVATION_API_BASE_URL + '/customer/' + customerId);
    },

    updateReservation: function(reservation, reservationId){
        return axios.put(RESERVATION_API_BASE_URL + '/' + reservationId, reservation);
    },

    deleteReservation: function(reservationId){
        return axios.delete(RESERVATION_API_BASE_URL + '/' + reservationId);
    },

    addReservation: function(customerId, flightId){
        const reservation = {
            "customerId": customerId,
            "flightId": flightId
        }
        return axios.post(RESERVATION_API_BASE_URL +'/', reservation);
    }
}

export default ReservationService;