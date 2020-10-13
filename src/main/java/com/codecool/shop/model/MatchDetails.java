package com.codecool.shop.model;

import java.util.Currency;

public class MatchDetails extends BaseModel {

    private String homeTeam;
    private String awayTeam;
    private String leagueName;
    private float homeOdds;
    private float drawOdds;
    private float awayOdds;

    private Country country;
    private SportType sportType;

    public MatchDetails(String description, String homeTeam, String awayTeam, String leagueName, float homeOdds, float drawOdds, float awayOdds, Country country, SportType sportType) {
        super(description);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.leagueName = leagueName;
        this.homeOdds = homeOdds;
        this.drawOdds = drawOdds;
        this.awayOdds = awayOdds;
        this.country = country;
        this.sportType = sportType;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public float getHomeOdds() {
        return homeOdds;
    }

    public void setHomeOdds(float homeOdds) {
        this.homeOdds = homeOdds;
    }

    public float getDrawOdds() {
        return drawOdds;
    }

    public void setDrawOdds(float drawOdds) {
        this.drawOdds = drawOdds;
    }

    public float getAwayOdds() {
        return awayOdds;
    }

    public void setAwayOdds(float awayOdds) {
        this.awayOdds = awayOdds;
    }

    public Country getCountry() {
        return country;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setCountry(Country country) {
        this.country = country;
        this.country.addMatchDetails(this);
    }


    public void setSportType(SportType sportType) {
        this.sportType = sportType;
        this.sportType.addMatchDetail(this);
    }

//    @Override
//    public String toString() {
//        return String.format("id: %1$d, " +
//                        "name: %2$s, " +
//                        "defaultPrice: %3$f, " +
//                        "defaultCurrency: %4$s, " +
//                        "productCategory: %5$s, " +
//                        "supplier: %6$s",
//                this.id,
//                this.country.getName(),
//                this.sportType);
//    }
}
