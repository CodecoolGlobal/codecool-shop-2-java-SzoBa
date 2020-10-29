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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


class MatchDetailsDaoJdbcTest {

    private MatchDetailsDaoJdbc matchDetailsDao;

    SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
    Country international = new Country("International", "Best country - Hungary.");
    private MatchDetails matchDetails = new MatchDetails("1. Match",
            "Italy", "Netherlands", "UEFA Nations League A, Gr. 1",
            1.95f, 3.3f, 4.0f, international, football);

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
        when(rs.getString("description")).thenReturn(matchDetails.getDescription());
        when(rs.getString("home_team")).thenReturn(matchDetails.getHomeTeam());
        when(rs.getString("away_team")).thenReturn(matchDetails.getAwayTeam());
        when(rs.getString("league_name")).thenReturn(matchDetails.getLeagueName());
        when(rs.getFloat("home_odds")).thenReturn(matchDetails.getHomeOdds());
        when(rs.getFloat("draw_odds")).thenReturn(matchDetails.getDrawOdds());
        when(rs.getFloat("away_odds")).thenReturn(matchDetails.getAwayOdds());
        when(countryDao.find(rs.getInt("country_id"))).thenReturn(matchDetails.getCountry());
        when(sportTypeDao.find(rs.getInt("sport_type_id"))).thenReturn(matchDetails.getSportType());

        MatchDetails returnedMatch = matchDetailsDao.find(1);
        assertEquals(matchDetails, returnedMatch);
    }

}