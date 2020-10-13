package com.codecool.shop.dao;

import com.codecool.shop.model.Country;

import java.util.List;

public interface CountryDao {

    void add(Country category);
    Country find(int id);
    void remove(int id);

    List<Country> getAll();

}
