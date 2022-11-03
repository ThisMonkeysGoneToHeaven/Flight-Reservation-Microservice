package com.dianant.reservationservice.models;

import java.time.ZonedDateTime;

public class Customer {

    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailId;

    private String password;

    private ZonedDateTime customerCreationDate;

    public Customer() {

    }

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
}