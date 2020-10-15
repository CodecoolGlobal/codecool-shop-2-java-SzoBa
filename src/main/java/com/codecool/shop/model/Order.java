package com.codecool.shop.model;

public class Order extends BaseModel {

    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String emailAddress;
    private String country;
    private String city;
    private int zip;
    private String Address;

    public Order(String description, String firstName, String lastName, int phoneNumber, String emailAddress, String country, String city, int zip, String address) {
        super(description);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.country = country;
        this.city = city;
        this.zip = zip;
        Address = address;
    }

    public Order(String description) {
        super(description);
    }
}
