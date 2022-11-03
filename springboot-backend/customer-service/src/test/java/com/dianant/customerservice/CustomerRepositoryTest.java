package com.dianant.customerservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByFirstNameIgnoreCaseContaining() {

        //given
        Customer customer = new Customer("Firstname", "Lastname", "0987654321", "email@domain.com", "password");
        underTest.save(customer);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        //when
        List<Customer> fetchedCustomers =  underTest.findByFirstNameIgnoreCaseContaining("Firstname");

        //then
        assertIterableEquals(customerList, fetchedCustomers);
    }

    @Test
    void findByLastNameIgnoreCaseContaining() {
        //given
        Customer customer = new Customer("Firstname", "Lastname", "0987654321", "email@domain.com", "password");
        underTest.save(customer);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        //when
        List<Customer> fetchedCustomers =  underTest.findByLastNameIgnoreCaseContaining("Lastname");

        //then
        assertIterableEquals(customerList, fetchedCustomers);
    }

    @Test
    void findByPhoneNumberContaining() {
        //given
        Customer customer = new Customer("Firstname", "Lastname", "0987654321", "email@domain.com", "password");
        underTest.save(customer);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        //when
        List<Customer> fetchedCustomers =  underTest.findByPhoneNumberContaining("54321");

        //then
        assertIterableEquals(customerList, fetchedCustomers);
    }

    @Test
    void findByEmailIdIgnoreCaseContaining() {
        //given
        Customer customer = new Customer("Firstname", "Lastname", "0987654321", "email@domain.com", "password");
        underTest.save(customer);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        //when
        List<Customer> fetchedCustomers =  underTest.findByEmailIdIgnoreCaseContaining("Domain");

        //then
        assertIterableEquals(customerList, fetchedCustomers);
    }

    @Test
    void existsByEmailId() {
        //given
        Customer customer = new Customer("Firstname", "Lastname", "0987654321", "email@domain.com", "password");
        underTest.save(customer);

        //when
        Boolean existsByEmail =  underTest.existsByEmailId("email@domain.com");

        //then
        assertEquals(true, existsByEmail);
    }

    @Test
    void existsByPhoneNumber() {
        //given
        Customer customer = new Customer("Firstname", "Lastname", "0987654321", "email@domain.com", "password");
        underTest.save(customer);

        //when
        Boolean existsByPhone =  underTest.existsByPhoneNumber("0987654321");

        //then
        assertEquals(true, existsByPhone);
    }

    @Test
    void findByEmailId() {
        //given
        Customer customer = new Customer("Firstname", "Lastname", "0987654321", "email@domain.com", "password");
        underTest.save(customer);

        //when
        Customer fetchedCustomer =  underTest.findByEmailId("email@domain.com").orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with such email found in tests!"));

        //then
        assertEquals(customer, fetchedCustomer);
    }
}