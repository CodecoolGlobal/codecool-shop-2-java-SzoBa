package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.SportType;

import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {

    private List<Cart> data = new ArrayList<>();
    private static CartDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public void setData(List<Cart> data) {
        this.data = data;
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {

    }

    @Override
    public void add(Cart cart, int clientSessionIdHashCode) {
        cart.setId(clientSessionIdHashCode);
        data.add(cart);
    }

    @Override
    public Cart find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void update(Cart cart) {

    }

    @Override
    public List<Cart> getAll() {
        return data;
    }
}
