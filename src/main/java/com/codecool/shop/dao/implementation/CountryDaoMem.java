package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryDaoMem implements CountryDao {

    private List<Country> data = new ArrayList<>();
    private static CountryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CountryDaoMem() {
    }

    public static CountryDaoMem getInstance() {
        if (instance == null) {
            instance = new CountryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Country category) {
        category.setId(data.size() + 1);
        data.add(category);
    }

    @Override
    public Country find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Country> getAll() {
        return data;
    }
}
