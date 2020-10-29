package com.codecool.shop.model;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class MatchDetails extends BaseModel {

    @Expose
    private String homeTeam;
    @Expose
    private String awayTeam;
    @Expose
    private String leagueName;
    @Expose
    private float homeOdds;
    @Expose
    private float drawOdds;
    @Expose
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

    @Override
    public String toString() {
        return String.format("Id: %d, Country: %s, Sport: %s",
                this.id,
                this.country.getName(),
                this.sportType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchDetails that = (MatchDetails) o;
        return Float.compare(that.homeOdds, homeOdds) == 0 &&
                Float.compare(that.drawOdds, drawOdds) == 0 &&
                Float.compare(that.awayOdds, awayOdds) == 0 &&
                homeTeam.equals(that.homeTeam) &&
                awayTeam.equals(that.awayTeam) &&
                leagueName.equals(that.leagueName) &&
                country.equals(that.country) &&
                sportType.equals(that.sportType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, leagueName, homeOdds, drawOdds, awayOdds, country, sportType);
    }
}