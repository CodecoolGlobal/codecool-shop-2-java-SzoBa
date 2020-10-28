package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.model.SportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SportTypeDaoMemTest {

    SportTypeDaoMem testSportTypeDaoMem;

    @BeforeEach
    void setup() {
        testSportTypeDaoMem = SportTypeDaoMem.getInstance();
        testSportTypeDaoMem.setData(new ArrayList<>());
        testSportTypeDaoMem.add(new SportType("Football", "Football description"));
        testSportTypeDaoMem.add(new SportType("Tennis", "Tennis description"));
        testSportTypeDaoMem.add(new SportType("Darts", "Darts description"));
    }


    @Test
    void add_multipleElementsAdded_returnsNumberOfElements() {
        assertEquals(3, testSportTypeDaoMem.getAll().size());
    }

    @Test
    void find_searchForNotExistingIndex_returnsNull() {
        assertNull(testSportTypeDaoMem.find(5));
    }

    @Test
    void find_searchForExistingIndex_returnsValue() {
        SportType sport4 = new SportType("Handball", "Handball description");
        testSportTypeDaoMem.add(sport4);
        assertEquals(sport4, testSportTypeDaoMem.find(4));
    }

    @Test
    void remove() {

    }

    @Test
    void getAll() {
    }
}