package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoMemTest {
    OrderDaoMem testOrderDaoMem;
    Order testOrder = new Order();

    @BeforeEach
    void setup() {
        testOrderDaoMem = OrderDaoMem.getInstance();
    }


    @Test
    void add_addElement_increaseListSize() {
        testOrderDaoMem.add(new Order());
        assertTrue(testOrderDaoMem.getListOfOrders().size() == 1);
    }


    @Test
    void remove_removeElementWithExistingId_decreasesListSize() {
        testOrder.setId(1);
        testOrderDaoMem.add(testOrder);
        int sizeBefore = testOrderDaoMem.getListOfOrders().size();
        testOrderDaoMem.remove(1);
        int sizeAfter = testOrderDaoMem.getListOfOrders().size();
        assertEquals(sizeAfter + 1, sizeBefore);
    }

    @Test
    void find_searchForPresentElement_ReturnsElement() {
        testOrder.setId(1);
        testOrderDaoMem.add(testOrder);
        assertNotNull(testOrderDaoMem.find(1));
    }

    @Test
    void find_searchForNotPresentElement_ReturnsNull() {
        testOrder.setId(1);
        testOrderDaoMem.add(testOrder);
        assertNull(testOrderDaoMem.find(2));
    }
    @Test
    void getListOfOrders() {
        assertTrue(testOrderDaoMem.getListOfOrders() instanceof List);
    }
}