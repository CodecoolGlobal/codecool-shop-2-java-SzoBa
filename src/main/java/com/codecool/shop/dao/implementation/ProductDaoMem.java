package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.MatchDetails;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.SportType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<MatchDetails> data = new ArrayList<>();
    private static ProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(MatchDetails matchDetails) {
        matchDetails.setId(data.size() + 1);
        data.add(matchDetails);
    }

    @Override
    public MatchDetails find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<MatchDetails> getAll() {
        return data;
    }

    @Override
    public List<MatchDetails> getBy(SportType sportType) {
        return data.stream().filter(t -> t.getSportType().equals(sportType)).collect(Collectors.toList());
    }

    @Override
    public List<MatchDetails> getBy(Country country) {
        return data.stream().filter(t -> t.getCountry().equals(country)).collect(Collectors.toList());
    }
}
