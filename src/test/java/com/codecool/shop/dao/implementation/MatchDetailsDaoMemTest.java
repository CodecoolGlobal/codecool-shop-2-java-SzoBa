package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import com.codecool.shop.model.SportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class MatchDetailsDaoMemTest {


    MatchDetailsDaoMem matchDetailsDaoMem;

    @BeforeEach
    void setUp() {
        matchDetailsDaoMem = MatchDetailsDaoMem.getInstance();
        matchDetailsDaoMem.setData(new ArrayList<>());
        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
        Country international = new Country("International", "Best country - Hungary.");
        matchDetailsDaoMem.add(new MatchDetails("1. Match",
                "Italy", "Netherlands", "UEFA Nations League A, Gr. 1",
                1.95f, 3.3f, 4.0f, international, football));

        matchDetailsDaoMem.add(new MatchDetails("2. Match",
                "England", "Denmark", "UEFA Nations League A, Gr. 2",
                1.83f, 3.4f, 4.5f, international, football));
    }

    @Test
    void add_validMatch_increaseListSize() {
        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
        Country international = new Country("International", "Best country - Hungary.");
        matchDetailsDaoMem.add(new MatchDetails("3. Match",
                "Iceland", "Belgium", "UEFA Nations League A, Gr. 2",
                11.0f, 5.5f, 1.28f, international, football));
        int expectedSize = 3;
        assertEquals(expectedSize, matchDetailsDaoMem.getAll().size());
    }

    @Test
    void find_validId_returnExistingMatch() {
        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
        Country international = new Country("International", "Best country - Hungary.");
        MatchDetails matchDetails = new MatchDetails("3. Match",
                "Iceland", "Belgium", "UEFA Nations League A, Gr. 2",
                11.0f, 5.5f, 1.28f, international, football);
        matchDetailsDaoMem.add(matchDetails);
        final int addedObjectId = 3;
        assertEquals(matchDetails, matchDetailsDaoMem.find(addedObjectId));
    }

    @Test
    void find_invalidId_returnNull() {
        final int invalidId = -1;
        assertNull(matchDetailsDaoMem.find(invalidId));
    }

    @Test
    void remove_validId_decreaseSize() {
        matchDetailsDaoMem.remove(1);
        int expectedSize = 1;
        assertEquals(expectedSize, matchDetailsDaoMem.getAll().size());
    }

    @Test
    void remove_validId_listNotContainsRemovedElement() {
        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
        Country international = new Country("International", "Best country - Hungary.");
        MatchDetails matchDetails = new MatchDetails("3. Match",
                "Iceland", "Belgium", "UEFA Nations League A, Gr. 2",
                11.0f, 5.5f, 1.28f, international, football);
        matchDetailsDaoMem.add(matchDetails);
        final int addedObjectId = 3;
        matchDetailsDaoMem.remove(addedObjectId);
        assertFalse(matchDetailsDaoMem.getAll().contains(matchDetails));
    }

    @Test
    void getBy_sportTypeSelectedAndMatchExisting_returnsFilteredList() {
        SportType tennis = new SportType("Tennis", "ico-sport ico-sport-tennis");
        Country international = new Country("International", "Best country - Hungary.");
        MatchDetails tennisMatch = new MatchDetails("16. Match",
                "Zverev A.", "Verdasco F.", "ATP",
                1.2f, 1.0f, 4.33f, international, tennis);

        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
        MatchDetails footballMatch = new MatchDetails("3. Match",
                "Iceland", "Belgium", "UEFA Nations League A, Gr. 2",
                11.0f, 5.5f, 1.28f, international, football);

        matchDetailsDaoMem.add(tennisMatch);
        matchDetailsDaoMem.add(footballMatch);
        final int expectedSize = 1;

        assertTrue(matchDetailsDaoMem.getBy(tennis).contains(tennisMatch));
        assertFalse(matchDetailsDaoMem.getBy(tennis).contains(footballMatch));
        assertEquals(expectedSize, matchDetailsDaoMem.getBy(tennis).size());
    }

    @Test
    void getBy_sportTypeSelectedAndMatchNotExisting_returnsNullList() {
        SportType tennis = new SportType("Tennis", "ico-sport ico-sport-tennis");
        final int expectedSize = 0;

        assertEquals(expectedSize, matchDetailsDaoMem.getBy(tennis).size());
    }

    @Test
    void getBy_countryTypeSelectedAndMatchExisting_returnsFilteredList() {
        Country hungary = new Country("Hungary", "Best country - Hungary.");
        Country england = new Country("England", "Tea for two.");
        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");

        MatchDetails hungarianFootballMatch = new MatchDetails("5. Match",
                "Budafoki MTE", "Diósgyőri VTK", "NB I",
                2.25f, 3.4f, 3.1f, hungary, football);
        MatchDetails englishFootballMatch = new MatchDetails("8. Match",
                "Everton", "Liverpool", "Premier League",
                3.75f, 4.2f, 1.8f, england, football);

        matchDetailsDaoMem.add(hungarianFootballMatch);
        matchDetailsDaoMem.add(englishFootballMatch);
        final int expectedSize = 1;

        assertTrue(matchDetailsDaoMem.getBy(hungary).contains(hungarianFootballMatch));
        assertFalse(matchDetailsDaoMem.getBy(hungary).contains(englishFootballMatch));
        assertEquals(expectedSize, matchDetailsDaoMem.getBy(hungary).size());
    }

    @Test
    void getBy_countryAndSportTypeSelectedAndMatchExisting_returnsFilteredList() {
        Country hungary = new Country("Hungary", "Best country - Hungary.");
        Country england = new Country("England", "Tea for two.");
        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
        SportType darts = new SportType("Darts", "ico-sport ico-sport-darts");

        MatchDetails hungarianFootballMatch = new MatchDetails("5. Match",
                "Budafoki MTE", "Diósgyőri VTK", "NB I",
                2.25f, 3.4f, 3.1f, hungary, football);
        MatchDetails englishFootballMatch = new MatchDetails("8. Match",
                "Everton", "Liverpool", "Premier League",
                3.75f, 4.2f, 1.8f, england, football);
        MatchDetails englishDartsMatch = new MatchDetails("25. Match",
                "Durrant G.", "Anderson G.", "Premier League, Playoffs",
                1.85f, 1.0f, 2.3f, england, darts);

        matchDetailsDaoMem.add(hungarianFootballMatch);
        matchDetailsDaoMem.add(englishFootballMatch);
        matchDetailsDaoMem.add(englishDartsMatch);
        final int expectedSize = 1;

        assertTrue(matchDetailsDaoMem.getBy(football, hungary).contains(hungarianFootballMatch));
        assertFalse(matchDetailsDaoMem.getBy(football, hungary).contains(englishFootballMatch));
        assertFalse(matchDetailsDaoMem.getBy(football, hungary).contains(englishDartsMatch));
        assertEquals(expectedSize, matchDetailsDaoMem.getBy(hungary).size());
    }


}