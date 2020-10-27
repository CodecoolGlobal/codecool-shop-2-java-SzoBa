package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.model.Country;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CountryDaoJdbc implements CountryDao {

    private final DataSource dataSource;

    public CountryDaoJdbc (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Country country) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT into country (name, description) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, country.getName());
            st.setString(2, country.getDescription());
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
        return null;
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM country ";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Country> getAll() {
        return null;
    }
}
