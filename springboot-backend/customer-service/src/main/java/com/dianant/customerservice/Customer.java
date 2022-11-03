package com.dianant.customerservice;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "First name cannot be blank!")
    private String firstName;
    private String lastName;

    @NotBlank(message = "Phone Number cannot be blank!")
    @Column(length = 10, unique = true)
    private String phoneNumber;

    @NotBlank(message = "Email Address cannot be blank!")
    @Email
    @Column(unique = true)
    private String emailId;

    @NotBlank(message = "Password cannot be blank!")
    private String password;

    private ZonedDateTime customerCreationDate;

    // default constructor
    public Customer(){

    }

    // constructor for non-auto-generated attributes
    public Customer(String firstName, String lastName, String phoneNumber, String emailId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.password = password;
    }

    // all values constructor
    public Customer(long id, String firstName, String lastName, String phoneNumber, String emailId, String password, ZonedDateTime customerCreationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.password = password;
        this.customerCreationDate = customerCreationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getCustomerCreationDate() {
        return customerCreationDate;
    }

    public void setCustomerCreationDate(ZonedDateTime customerCreationDate) {
        this.customerCreationDate = customerCreationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && firstName.equals(customer.firstName) && Objects.equals(lastName, customer.lastName) && phoneNumber.equals(customer.phoneNumber) && emailId.equals(customer.emailId) && password.equals(customer.password) && Objects.equals(customerCreationDate, customer.customerCreationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNumber, emailId, password, customerCreationDate);
    }
}