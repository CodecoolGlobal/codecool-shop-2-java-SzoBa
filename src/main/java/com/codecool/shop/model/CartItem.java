package com.codecool.shop.model;

import com.google.gson.annotations.Expose;

public class CartItem extends BaseModel {
    @Expose
    protected String home;
    @Expose
    protected String away;
    @Expose
    protected String chosenOutcome;
    @Expose
    protected String leagueName;
    @Expose
    protected float odds;
    @Expose
    protected int matchId;

    public CartItem(String description, MatchDetails matchDetails, String outcome) {
        super(description);
        this.home = matchDetails.getHomeTeam();
        this.away = matchDetails.getAwayTeam();
        this.leagueName = matchDetails.getLeagueName();
        switch (outcome) {
            case ("home"):
                this.chosenOutcome = "Hazai";
                this.odds = matchDetails.getHomeOdds();
                break;
            case ("draw"):
                this.chosenOutcome = "Döntetlen";
                this.odds = matchDetails.getDrawOdds();
                break;
            case ("away"):
                this.chosenOutcome = "Vendég";
                this.odds = matchDetails.getAwayOdds();
                break;
        }
        this.matchId = matchDetails.getId();
    }

    public int getMatchId() {
        return matchId;
    }

    public String getHome() {
        return home;
    }

    public String getAway() {
        return away;
    }

    public String getChosenOutcome() {
        return chosenOutcome;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public float getOdds() {
        return odds;
    }
}
