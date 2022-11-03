package com.dianant.customerservice;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping(value = "/")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,@Valid @RequestBody Customer updatedCustomer){
        return customerService.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id);
    }

    // we don't want validation enabled here
    @PostMapping(value = "/login")
    public ResponseEntity<Boolean> loginCustomer(@RequestBody Customer customer){
        return customerService.loginCustomer(customer);
    }

    @GetMapping(value = "/email/{emailId}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String emailId){
        return customerService.getCustomerByEmail(emailId);
    }

    @GetMapping(value = "/search/name")
    public ResponseEntity<List<Customer>> searchCustomersByName(@RequestParam String name){
        return customerService.searchCustomersByName(name);
    }

    @GetMapping(value = "/search/phone")
    public ResponseEntity<List<Customer>> searchCustomersByPhone(@RequestParam String phone){
        return customerService.searchCustomersByPhone(phone);
    }

    @GetMapping(value = "/search/email")
    public ResponseEntity<List<Customer>> searchCustomersByEmail(@RequestParam String email){
        return customerService.searchCustomersByEmail(email);
    }

}