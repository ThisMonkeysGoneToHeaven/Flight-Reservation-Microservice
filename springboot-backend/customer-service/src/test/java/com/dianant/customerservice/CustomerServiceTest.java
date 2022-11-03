package com.dianant.customerservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService underTest;

    @Test
    void canGetAllCustomers() {
        // given
        Customer customer = new Customer("FirstName", "LastName", "0987654321", "email@domain.com", "password");
        // when
        Mockito.lenient().when(customerRepository.findAll()).thenReturn(Stream.of(customer).collect(Collectors.toList()));
        // then
        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void canCreateCustomer() {

        // when
        Customer customer = new Customer(1,"Firstname", "Lastname", "0987654321", "email@domain.com", "password", ZonedDateTime.now());

        //given
        Mockito.lenient().when(customerRepository.existsByEmailId(anyString())).thenReturn(false);
        Mockito.lenient().when(customerRepository.existsByPhoneNumber(anyString())).thenReturn(false);
        Mockito.lenient().when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // then
        ResponseEntity<Customer> fetchedCustomer = underTest.createCustomer(customer);
        assertEquals(ResponseEntity.ok(customer), fetchedCustomer);
    }

    @Test
    void canGetCustomerById() {
        // when
        Customer customer = new Customer(1,"Firstname", "Lastname", "0987654321", "email@domain.com", "password", ZonedDateTime.now());

        //given
        Mockito.lenient().when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // then
        Customer fetchedCustomer = underTest.getCustomerById(customer.getId()).getBody();
        assertEquals(customer, fetchedCustomer);
    }

    @Test
    void canUpdateCustomer() {
        // given
        Customer oldCustomer = new Customer(1,"Firstname", "Lastname", "0987654321", "email@domain.com", "password", ZonedDateTime.now());
        Customer updatedCustomer = new Customer(1,"New-Firstname", "Lastname", "0987654321", "email@domain.com", "password", ZonedDateTime.now());

        // when
        Mockito.lenient().when(customerRepository.existsById(anyLong())).thenReturn(true);
        Mockito.lenient().when(customerRepository.getById(anyLong())).thenReturn(oldCustomer);
        Mockito.lenient().when(customerRepository.existsByPhoneNumber(anyString())).thenReturn(false);
        Mockito.lenient().when(customerRepository.existsByEmailId(anyString())).thenReturn(false);
        Mockito.lenient().when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
        // then
        assertEquals(ResponseEntity.ok(updatedCustomer), underTest.updateCustomer(1L, updatedCustomer));
    }

    @Test
    void canDeleteCustomer() {
        //given
        Customer customer = new Customer(1,"Firstname", "Lastname", "0987654321", "email@domain.com", "password", ZonedDateTime.now());
        //when
        Mockito.lenient().when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        Mockito.lenient().doNothing().when(customerRepository).deleteById(anyLong());
        //then
        assertEquals(ResponseEntity.ok(customer), underTest.deleteCustomer(1L));
    }

    @Test
    @Disabled
    void canSearchCustomersByName() {
    }

    @Test
    @Disabled
    void canSearchCustomersByPhone() {
    }

    @Test
    void canSearchCustomersByEmail() {
    }

    @Test
    void canLoginCustomer() {
        //given
        Customer customer = new Customer(1,"Firstname", "Lastname", "0987654321", "email@domain.com", "password", ZonedDateTime.now());
        //when
        Mockito.lenient().when(customerRepository.findByEmailId(anyString())).thenReturn(Optional.of(customer));
        //then
        assertEquals(ResponseEntity.ok(true), underTest.loginCustomer(customer));
    }
}