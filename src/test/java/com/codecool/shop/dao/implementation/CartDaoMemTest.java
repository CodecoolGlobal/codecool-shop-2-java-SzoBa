package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Cart;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartDaoMemTest {

    CartDaoMem testCartDaoMem;
    Cart testCart1;
    Cart testCart2;

    @BeforeEach
    void setup() {
        testCart1 = new Cart("teszti");
        testCart2 = new Cart("teszt2");
        testCartDaoMem = CartDaoMem.getInstance();
        testCartDaoMem.setData(new ArrayList<>());
        testCartDaoMem.add(testCart1, 20);
        testCartDaoMem.add(testCart2, 10);


    }

//    @org.junit.jupiter.api.Test
//    void getInstance() {
//    }

    @org.junit.jupiter.api.Test
    void add_addItem_increaseCartSize() {
        assertEquals(2, testCartDaoMem.getAll().size());
    }

//    @org.junit.jupiter.api.Test
//    void testAdd() {
//    }

    @org.junit.jupiter.api.Test
    void find_searchForNotExistingIndex_returnsNull() {
        assertNull(testCartDaoMem.find(-1));
    }

    @org.junit.jupiter.api.Test
    void remove_removeItem_return() {
        testCartDaoMem.remove(10);
        assertNull(testCartDaoMem.find(10));
    }

    @org.junit.jupiter.api.Test
    void update() {
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        assertEquals(2, testCartDaoMem.getAll().size());
    }
}