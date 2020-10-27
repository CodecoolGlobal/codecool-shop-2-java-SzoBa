package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;

import java.util.List;

public interface CartDao {

    void add(Cart cart);
    void add(Cart cart, int clientSessionHashCode);
    Cart find(int id);
    void remove(int id);
    void update(Cart cart);
    List<Cart> getAll();
}
