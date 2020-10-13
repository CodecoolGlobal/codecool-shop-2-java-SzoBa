package com.codecool.shop.model;

public class CartItem extends BaseModel {
    protected String home;
    protected String away;
    protected String chosenOutcome;
    protected String leagueName;
    protected float odds;
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
}
