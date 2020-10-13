package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class SportType extends BaseModel {

    private String name;
    private List<MatchDetails> matchDetails;

    public SportType(String name, String description) {
        super(description);
        this.name = name;
        this.matchDetails = new ArrayList<>();
    }

    public void setMatchDetails(ArrayList<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }

    public List<MatchDetails> getMatchDetails() {
        return this.matchDetails;
    }

    public void addMatchDetail(MatchDetails matchDetails) {
        this.matchDetails.add(matchDetails);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}