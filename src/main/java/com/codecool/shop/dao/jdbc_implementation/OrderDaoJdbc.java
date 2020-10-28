package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;

public class OrderDaoJdbc implements OrderDao {

    private DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO confirmed_order (id," +
                    " first_name," +
                    " last_name," +
                    " phone_number," +
                    " email_address," +
                    " country," +
                    " zip," +
                    " address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, order.getId());
            statement.setString(2, order.getFirstName());
            statement.setString(3, order.getLastName());
            statement.setString(4, order.getPhoneNumber());
            statement.setString(5, order.getEmailAddress());
            statement.setString(6, order.getCountry());
            statement.setInt(7, order.getZip());
            statement.setString(8, order.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM confirmed_order " +
                         "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt;
            String query =
                    "SELECT * " +
                    "FROM confirmed_order " +
                    "WHERE id = ?";

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Order requestedOrder = getOrder(rs);
            requestedOrder.setId(id);

            return requestedOrder;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Order getOrder(ResultSet rs) throws SQLException {
        String description = "description";
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String phoneNumber = rs.getString("phone_number");
        String emailAddress = rs.getString("email_address");
        String country = rs.getString("country");
        String city = "city";
        int zip = rs.getInt("zip");
        String address = rs.getString("address");

        return new Order(description, firstName, lastName, phoneNumber,
                        emailAddress, country, city, zip, address);
    }
}
