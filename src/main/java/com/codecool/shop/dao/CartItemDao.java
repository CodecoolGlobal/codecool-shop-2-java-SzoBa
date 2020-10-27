package com.codecool.shop.dao;

import com.codecool.shop.model.CartItem;

import java.util.List;

public interface CartItemDao {

    void add(CartItem cartItem, int cartId);
    CartItem find(int cartId, int matchId);
    void remove(int cartId, int matchId);
    void removeAll(int cartId);

    List<CartItem> getAllByCart(int cartId);
//    List<CartItem> getAll();

}
