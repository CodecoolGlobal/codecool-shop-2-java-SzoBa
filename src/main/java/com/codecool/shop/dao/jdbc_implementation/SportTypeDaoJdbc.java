package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.SportType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SportTypeDaoJdbc implements SportTypeDao {

    private final DataSource dataSource;

    public SportTypeDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(SportType sportType) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT into sport_type (name, description) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, sportType.getName());
            st.setString(2, sportType.getDescription());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            sportType.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SportType find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM sport_type WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();

            SportType requestedSportType = getSportType(rs);
            requestedSportType.setId(id);
            return requestedSportType;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SportType getSportType(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String description = rs.getString("description");
        return new SportType(name, description);
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM sport_type WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SportType> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description FROM sport_type";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<SportType> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String countryName = rs.getString("name");
                String description = rs.getString("description");
                SportType sportType = new SportType(countryName, description);
                sportType.setId(id);
                result.add(sportType);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
