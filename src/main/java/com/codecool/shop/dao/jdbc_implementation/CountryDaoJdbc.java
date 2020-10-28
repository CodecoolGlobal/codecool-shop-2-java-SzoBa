package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.model.Country;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoJdbc implements CountryDao {

    private final DataSource dataSource;

    public CountryDaoJdbc (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Country country) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT into country (name) VALUES (?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, country.getName());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            country.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Country find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name FROM country WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            Country requestedCountry = getCountry(rs);
            requestedCountry.setId(id);
            return requestedCountry;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Country getCountry(ResultSet rs) throws SQLException {
//        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = "description";

        return new Country(name, description);
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM country WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Country> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name FROM country";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<Country> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String countryName = rs.getString("name");

                Country country = new Country(countryName, "description");
                country.setId(id);
                result.add(country);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
