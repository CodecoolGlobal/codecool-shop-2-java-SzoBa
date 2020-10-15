package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;

public class OrderDaoMem implements OrderDao {

    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String emailAddress;
    private String country;
    private String city;
    private int zip;
    private String Address;

    public OrderDaoMem(String firstName, String lastName, String emailAddress, String country, String city, int zip, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.country = country;
        this.city = city;
        this.zip = zip;
        Address = address;
    }

    @Override
    public void add(Order order) {

    }
}
