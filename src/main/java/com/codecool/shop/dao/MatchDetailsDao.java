package com.codecool.shop.dao;

import com.codecool.shop.model.MatchDetails;
import com.codecool.shop.model.SportType;
import com.codecool.shop.model.Country;

import java.util.List;

public interface MatchDetailsDao {

    void add(MatchDetails matchDetails);
    MatchDetails find(int id);
    void remove(int id);

    List<MatchDetails> getAll();
    List<MatchDetails> getBy(SportType sportType);
    List<MatchDetails> getBy(Country country);

}
