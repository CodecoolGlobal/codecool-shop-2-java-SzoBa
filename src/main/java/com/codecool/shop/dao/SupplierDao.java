package com.codecool.shop.dao;

import com.codecool.shop.model.SportType;

import java.util.List;

public interface SupplierDao {

    void add(SportType sportType);
    SportType find(int id);
    void remove(int id);

    List<SportType> getAll();
}
