package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart extends BaseModel{

    protected List<CartItem> items = new ArrayList<>();
    protected float bet;
    protected Date date;



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
