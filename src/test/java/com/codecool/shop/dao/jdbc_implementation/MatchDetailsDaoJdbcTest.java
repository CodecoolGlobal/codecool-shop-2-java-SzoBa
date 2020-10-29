package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import com.codecool.shop.model.SportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


class MatchDetailsDaoJdbcTest {

    private MatchDetailsDaoJdbc matchDetailsDao;

    SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
    SportType tennis = new SportType("Tennis", "ico-sport ico-sport-tennis");
    SportType iceHockey = new SportType("Hockey", "ico-sport ico-sport-ice-hockey");

    Country international = new Country("International", "Best country - Hungary.");
    Country russia = new Country("Russia", "Voszem");
    Country england = new Country("England", "Tea for two.");

    private MatchDetails matchDetails = new MatchDetails("1. Match",
            "Italy", "Netherlands", "UEFA Nations League A, Gr. 1",
            1.95f, 3.3f, 4.0f, international, football);

    private MatchDetails englishFootball = new MatchDetails("10. Match",
                               "Manchester City", "Arsenal", "Premier League",
                               1.4f, 5.25f, 7.0f, england, football);

    private MatchDetails internationalTennis = new MatchDetails("16. Match",
                        "Zverev A.", "Verdasco F.", "ATP",
                        1.2f, 1.0f, 4.33f, international, tennis);

    private MatchDetails russianHockey = new MatchDetails("21. Match",
                            "Kunlun Red Star", "Salavat Yulaev Ufa", "KHL",
                            3.8f, 1.0f, 1.28f, russia, iceHockey);

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    @Mock
    private CountryDao countryDao;

    @Mock
    private SportTypeDao sportTypeDao;


    @BeforeEach
    void setUp() throws SQLException {
        matchDetails.setId(1);
        MockitoAnnotations.initMocks(this);
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(conn.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(dataSource.getConnection()).thenReturn(conn);
        matchDetailsDao = new MatchDetailsDaoJdbc(dataSource, countryDao, sportTypeDao);
    }

    @Test
    void add_existingMatch_getSameMatchAfterAdd() throws SQLException {
        final int expectedId = 1001;
        when(stmt.executeUpdate()).thenReturn(0);
        when(stmt.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1001);
        matchDetailsDao.add(matchDetails);

        assertEquals(expectedId, matchDetails.getId());
    }

    @Test
    void find_existingMatchId_getMatch() throws SQLException {
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        getMockedMatchDetails(matchDetails);

        MatchDetails returnedMatch = matchDetailsDao.find(1);
        assertEquals(matchDetails, returnedMatch);
    }

    @Test
    void getAll_collectAllMatches_returnMatches() throws SQLException {
        when(stmt.executeQuery()).thenReturn(rs);
        List<MatchDetails> matches = new ArrayList<>();
        matches.add(matchDetails);
        matches.add(englishFootball);
        matches.add(russianHockey);
        matches.add(internationalTennis);

        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getString("description")).thenReturn(matchDetails.getDescription())
                .thenReturn(englishFootball.getDescription())
                .thenReturn(russianHockey.getDescription())
                .thenReturn(internationalTennis.getDescription());
        when(rs.getString("home_team")).thenReturn(matchDetails.getHomeTeam())
                .thenReturn(englishFootball.getHomeTeam())
                .thenReturn(russianHockey.getHomeTeam())
                .thenReturn(internationalTennis.getHomeTeam());
        when(rs.getString("away_team")).thenReturn(matchDetails.getAwayTeam())
                .thenReturn(englishFootball.getAwayTeam())
                .thenReturn(russianHockey.getAwayTeam())
                .thenReturn(internationalTennis.getAwayTeam());
        when(rs.getString("league_name")).thenReturn(matchDetails.getLeagueName())
                .thenReturn(englishFootball.getLeagueName())
                .thenReturn(russianHockey.getLeagueName())
                .thenReturn(internationalTennis.getLeagueName());
        when(rs.getFloat("home_odds")).thenReturn(matchDetails.getHomeOdds())
                .thenReturn(englishFootball.getHomeOdds())
                .thenReturn(russianHockey.getHomeOdds())
                .thenReturn(internationalTennis.getHomeOdds());
        when(rs.getFloat("draw_odds")).thenReturn(matchDetails.getDrawOdds())
                .thenReturn(englishFootball.getDrawOdds())
                .thenReturn(russianHockey.getDrawOdds())
                .thenReturn(internationalTennis.getDrawOdds());
        when(rs.getFloat("away_odds")).thenReturn(matchDetails.getAwayOdds())
                .thenReturn(englishFootball.getAwayOdds())
                .thenReturn(russianHockey.getAwayOdds())
                .thenReturn(internationalTennis.getAwayOdds());
        when(countryDao.find(rs.getInt("country_id"))).thenReturn(matchDetails.getCountry())
                .thenReturn(englishFootball.getCountry())
                .thenReturn(russianHockey.getCountry())
                .thenReturn(internationalTennis.getCountry());
        when(sportTypeDao.find(rs.getInt("sport_type_id"))).thenReturn(matchDetails.getSportType())
                .thenReturn(englishFootball.getSportType())
                .thenReturn(russianHockey.getSportType())
                .thenReturn(internationalTennis.getSportType());

        List<MatchDetails> returnedMatches = matchDetailsDao.getAll();
        assertEquals(matches, returnedMatches);
    }

    private void getMockedMatchDetails(MatchDetails matchDetails) throws SQLException {
        when(rs.getString("description")).thenReturn(matchDetails.getDescription());
        when(rs.getString("home_team")).thenReturn(matchDetails.getHomeTeam());
        when(rs.getString("away_team")).thenReturn(matchDetails.getAwayTeam());
        when(rs.getString("league_name")).thenReturn(matchDetails.getLeagueName());
        when(rs.getFloat("home_odds")).thenReturn(matchDetails.getHomeOdds());
        when(rs.getFloat("draw_odds")).thenReturn(matchDetails.getDrawOdds());
        when(rs.getFloat("away_odds")).thenReturn(matchDetails.getAwayOdds());
        when(countryDao.find(rs.getInt("country_id"))).thenReturn(matchDetails.getCountry());
        when(sportTypeDao.find(rs.getInt("sport_type_id"))).thenReturn(matchDetails.getSportType());
    }

}