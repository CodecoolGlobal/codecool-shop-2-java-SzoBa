package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Country extends BaseModel {

    private String name;
    private List<MatchDetails> matchDetails;

    public Country(String name, String description) {
        super(description);
        this.name = name;
        this.matchDetails = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMatchDetails(ArrayList<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }

    public List<MatchDetails> getMatchDetails() {
        return this.matchDetails;
    }

    public void addMatchDetails(MatchDetails matchDetails) {
        this.matchDetails.add(matchDetails);
    }

    @Override
    public String toString() {
        return String.format(
                "id: %d," +
                        "name: %s, " +
                        "description: %s",
                this.id,
                this.name,
                this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}