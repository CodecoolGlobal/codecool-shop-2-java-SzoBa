package com.codecool.shop.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart extends BaseModel{
    @Expose
    protected List<CartItem> items = new ArrayList<>();
    @Expose
    protected float bet;
    @Expose
    protected Date date;
    @Expose
    protected int possibleWin;


    public Cart(String description) {
        super(description);
    }


    public List<CartItem> getItems() {
        return items;
    }

    public float getBet() {
        return bet;
    }

    public void setBet(float bet) {
        this.bet = bet;
    }

    public Date getActualTime() {
        return date;
    }

    public void setActualTime(Date actualTime) {
        date = actualTime;
    }

    public int getPossibleWin() {
        return possibleWin;
    }

    public void setPossibleWin(int possibleWin) {
        this.possibleWin = possibleWin;
    }

    public void addItemToCart(CartItem cartItem) {
        items.add(cartItem);
    }

    public void removeItemFromCart(int matchId) {
        for (CartItem cartItem : items) {
            if (cartItem.getMatchId() == matchId) {
                items.remove(cartItem);
                break;
            }
        }
    }
}
