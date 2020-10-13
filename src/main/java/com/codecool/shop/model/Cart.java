package com.codecool.shop.model;

import java.util.Date;

public class Cart extends BaseModel{

    protected String homeTeam;
    protected String awayTeam;
    protected String chosenTeam;
    protected String leagueName;
    protected float odds;
    protected float bet;
    protected Date ActualTime;



    public Cart(String description) {
        super(description);
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

    public String getChosenTeam() {
        return chosenTeam;
    }

    public void setChosenTeam(String chosenTeam) {
        this.chosenTeam = chosenTeam;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }

    public float getBet() {
        return bet;
    }

    public void setBet(float bet) {
        this.bet = bet;
    }

    public Date getActualTime() {
        return ActualTime;
    }

    public void setActualTime(Date actualTime) {
        ActualTime = actualTime;
    }
}
