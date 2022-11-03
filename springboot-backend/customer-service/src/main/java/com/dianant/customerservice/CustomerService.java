package com.dianant.customerservice;

import java.util.List;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    public ResponseEntity<Customer> createCustomer(Customer customer) {

        // checking if a customer with this phone number exists or not
        if(customerRepository.existsByPhoneNumber(customer.getPhoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already in use. Please choose a different phone number");
        }

        // checking if a customer with this email exists or not
        if(customerRepository.existsByEmailId(customer.getEmailId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Address already in use. Please choose a different Email-Id");
        }

        // setting up customer creation date
        customer.setCustomerCreationDate(ZonedDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    public ResponseEntity<Customer> getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with id: " + id + " exists in the database!"));

        return ResponseEntity.ok(customer);
    }

    public ResponseEntity<Customer> updateCustomer(Long id, Customer updatedCustomer) {

        if(! customerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"No customer with Id: " + id + " found in database. Please enter a valid customer id.");
        }

        Customer oldCustomer = customerRepository.getById(id);

        // checking if phone field has been altered in this update or not (ignore case)
        if(! oldCustomer.getPhoneNumber().equalsIgnoreCase(updatedCustomer.getPhoneNumber())){
            // making sure that this newly entered phone number isn't conflicting with anybody else's
            if(customerRepository.existsByPhoneNumber(updatedCustomer.getPhoneNumber())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This Phone Number Already Exists in the DB. Please enter a new phone number!");
            }
        }

        // checking if email field has been altered in this update or not (Ignore Case)
        if(! oldCustomer.getEmailId().equalsIgnoreCase(updatedCustomer.getEmailId())){
            // making sure that this newly entered emailId isn't conflicting with anybody else's
            if(customerRepository.existsByEmailId(updatedCustomer.getEmailId())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This Email Address Already Exists in the DB. Please enter a new email address!");
            }
        }

        updatedCustomer.setId(id);
        updatedCustomer.setCustomerCreationDate(oldCustomer.getCustomerCreationDate());
        Customer savedUpdatedCustomer = customerRepository.save(updatedCustomer);
        return ResponseEntity.ok(savedUpdatedCustomer);
    }

    public ResponseEntity<Customer> deleteCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"No customer with Id: " + customerId + " found in database. Please enter a valid customer id."));

        // we need to cancel all the reservations made by this customer first before deleting this customer
        String deleteReservationsForCustomerURL = "http://reservation-microservice/api/reservations/customer/" + customerId;
        restTemplate.delete(deleteReservationsForCustomerURL);

        customerRepository.delete(customer);
        return ResponseEntity.ok(customer);
    }

    public ResponseEntity<List<Customer>> searchCustomersByName(String value) {

        List<Customer> searchResults = customerRepository.findByFirstNameIgnoreCaseContaining(value);
        List<Customer> lastNameResults = customerRepository.findByLastNameIgnoreCaseContaining(value);
        searchResults.addAll(lastNameResults);

        return ResponseEntity.ok(searchResults);
    }

    public ResponseEntity<List<Customer>> searchCustomersByPhone(String value) {
        List<Customer> searchResults = customerRepository.findByPhoneNumberContaining(value);
        return ResponseEntity.ok(searchResults);
    }

    public ResponseEntity<List<Customer>> searchCustomersByEmail(String value) {
        List<Customer> searchResults = customerRepository.findByEmailIdIgnoreCaseContaining(value);
        return ResponseEntity.ok(searchResults);
    }

    public ResponseEntity<Boolean> loginCustomer(Customer customer) {
        String email = customer.getEmailId();
        String password = customer.getPassword();
        boolean allowLogin = false;

        Customer customerInDB = customerRepository.findByEmailId(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer record with email id : " + email + " found in the database. Try again with a correct email address!"));

        if(customerInDB.getPassword().equals(password))
            allowLogin = true;

        return ResponseEntity.ok(allowLogin);
    }

    public ResponseEntity<Customer> getCustomerByEmail(String emailId) {
        Customer customer = customerRepository.findByEmailId(emailId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"No customer with Email: " + emailId + " found in database. Please enter a valid customer email."));

        return ResponseEntity.ok(customer);
    }
}