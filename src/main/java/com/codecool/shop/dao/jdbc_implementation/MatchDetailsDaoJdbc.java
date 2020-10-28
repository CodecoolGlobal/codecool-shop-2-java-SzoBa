package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import com.codecool.shop.model.SportType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDetailsDaoJdbc implements MatchDetailsDao {

    private DataSource dataSource;
    private CountryDao countryDao;
    private SportTypeDao sportTypeDao;

    public MatchDetailsDaoJdbc(DataSource dataSource, CountryDao countryDao, SportTypeDao sportTypeDao) {
        this.dataSource = dataSource;
        this.countryDao = countryDao;
        this.sportTypeDao = sportTypeDao;
    }

    @Override
    public void add(MatchDetails matchDetails) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO match_details (" +
                    " home_team," +
                    " away_team," +
                    " league_name," +
                    " home_odds," +
                    " draw_odds," +
                    " away_odds," +
                    " country_id," +
                    " sport_type_id," +
                    " description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, matchDetails.getId());
            statement.setString(1, matchDetails.getHomeTeam());
            statement.setString(2, matchDetails.getAwayTeam());
            statement.setString(3, matchDetails.getLeagueName());
            statement.setFloat(4, matchDetails.getHomeOdds());
            statement.setFloat(5, matchDetails.getDrawOdds());
            statement.setFloat(6, matchDetails.getAwayOdds());
            statement.setInt(7, matchDetails.getCountry().getId());
            statement.setInt(8, matchDetails.getSportType().getId());
            statement.setString(9, matchDetails.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            matchDetails.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MatchDetails find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt;
            String query =
                    "SELECT * " +
                    "FROM match_details " +
                    "WHERE id = ?";

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            MatchDetails requestedMatch = getMatchDetails(rs);
            requestedMatch.setId(id);

            return requestedMatch;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        // TODO
    }

    @Override
    public List<MatchDetails> getAll() {
        try (Connection conn = dataSource.getConnection()){
            List<MatchDetails> matches = new ArrayList<>();
            PreparedStatement stmt;
            String query =
                    "SELECT * " +
                    "FROM match_details";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MatchDetails matchDetails = getMatchDetails(rs);
                matchDetails.setId(rs.getInt("id"));
                matches.add(matchDetails);
            }
            return matches;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatchDetails> getBy(SportType sportType) {
        try (Connection conn = dataSource.getConnection()){
            List<MatchDetails> matches = new ArrayList<>();
            PreparedStatement stmt;
            String query =
                    "SELECT * " +
                    "FROM match_details " +
                    "WHERE sport_type_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, sportType.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MatchDetails matchDetails = getMatchDetails(rs);
                matchDetails.setId(rs.getInt("id"));
                matches.add(matchDetails);
            }
            return matches;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatchDetails> getBy(Country country) {
        try (Connection conn = dataSource.getConnection()){
            List<MatchDetails> matches = new ArrayList<>();
            PreparedStatement stmt;
            String query =
                    "SELECT * " +
                    "FROM match_details " +
                    "WHERE country_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, country.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MatchDetails matchDetails = getMatchDetails(rs);
                matchDetails.setId(rs.getInt("id"));
                matches.add(matchDetails);
            }
            return matches;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatchDetails> getBy(SportType sportType, Country country) {
        try (Connection conn = dataSource.getConnection()){
            List<MatchDetails> matches = new ArrayList<>();
            PreparedStatement stmt;
            String query =
                    "SELECT * " +
                    "FROM match_details " +
                    "WHERE sport_type_id = ? AND country_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, sportType.getId());
            stmt.setInt(2, country.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MatchDetails matchDetails = getMatchDetails(rs);
                matchDetails.setId(rs.getInt("id"));
                matches.add(matchDetails);
            }
            return matches;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MatchDetails getMatchDetails(ResultSet rs) throws SQLException {
        String description = rs.getString("description");
        String homeTeam = rs.getString("home_team");
        String awayTeam = rs.getString("away_team");
        String leagueName = rs.getString("league_name");
        float homeOdds = rs.getFloat("home_odds");
        float drawOdds = rs.getFloat("draw_odds");
        float awayOdds = rs.getFloat("away_odds");
        Country country = countryDao.find(rs.getInt("country_id"));
        SportType sportType = sportTypeDao.find(rs.getInt("sport_type_id"));

        return new MatchDetails(description, homeTeam, awayTeam,
                                leagueName, homeOdds, drawOdds,
                                awayOdds, country, sportType);
    }
}
