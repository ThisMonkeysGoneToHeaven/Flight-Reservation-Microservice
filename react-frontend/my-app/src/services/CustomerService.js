import axios from 'axios';

const CUSTOMER_API_BASE_URL = "http://localhost:9004/api/customers";

const CustomerService = {

    // getCustomers: function(){
    //     return axios.get(CUSTOMER_API_BASE_URL);
    // },

    getCustomerById: function(customerId){
        return axios.get(CUSTOMER_API_BASE_URL + '/' + customerId);
    },

    updateCustomer: function(customer, customerId){
        return axios.put(CUSTOMER_API_BASE_URL + '/' + customerId, customer);
    },

    deleteCustomer: function(customerId){
        return axios.delete(CUSTOMER_API_BASE_URL + '/' + customerId);
    },

    getCustomerByEmail: function(customerEmail){
        return axios.get(CUSTOMER_API_BASE_URL + '/email/' + customerEmail);
    },

    addCustomer: function(customer){
        return axios.post(CUSTOMER_API_BASE_URL +'/', customer);
    },

    // searchCustomers: function(parameter, query){
    //     return axios.get(CUSTOMER_API_BASE_URL + "/search/" + parameter + '/' + query);
    // }
}

export default CustomerService;