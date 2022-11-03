import axios from 'axios';

const FLIGHT_API_BASE_URL = "http://localhost:9004/api/flights";

const FlightService = {

    getAllFlights: function(){
        return axios.get(FLIGHT_API_BASE_URL + '/');
    },

    getFlightById: function(flightId){
        return axios.get(FLIGHT_API_BASE_URL + '/' + flightId);
    },

}

export default FlightService;