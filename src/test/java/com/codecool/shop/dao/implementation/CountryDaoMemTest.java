package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryDaoMemTest {
    CountryDaoMem testCountryDaoMem;

    @BeforeEach
    void setup() {
        testCountryDaoMem = CountryDaoMem.getInstance();
    }

    @Test
    void add_addElement_increaseListSize() {
        testCountryDaoMem.add(new Country("Hungary", "home"));
        assertTrue(testCountryDaoMem.getAll().size() == 1);
    }

    @Test
    void find_searchForNotPresentIndex_ReturnsNull() {
        assertNull(testCountryDaoMem.find(1000));
    }

    @Test
    void find_searchForPresentIndex_ReturnsCorrectItem() {
        testCountryDaoMem.add(new Country("Hungary", "home"));
        assertNotNull(testCountryDaoMem.find(1));
    }

    @Test
    void remove_removeItem_ReducesListSizeByOne() {
        testCountryDaoMem.add(new Country("Hungary", "home"));
        int sizeBefore = testCountryDaoMem.getAll().size();
        testCountryDaoMem.remove(1);
        int sizeAfter = testCountryDaoMem.getAll().size();
        assertEquals(sizeAfter + 1, sizeBefore);
    }

    @Test
    void getAll_returnsList_of_Countries() {
        assertTrue(testCountryDaoMem.getAll() instanceof List);
    }
}