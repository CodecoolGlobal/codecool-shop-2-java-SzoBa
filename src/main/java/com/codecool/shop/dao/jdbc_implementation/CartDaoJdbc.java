package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJdbc implements CartDao {
    private final DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;  // TODO this required
    }


    @Override
    public void add(Cart cart) {
    }


    @Override
    public void add(Cart cart, int clientSessionIdHashCode) {
        cart.setId(clientSessionIdHashCode);
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart" +
                    "(id, actual_time)" +
                    "VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            statement.setInt(1, cart.getId());
            statement.setDate(2, (Date) cart.getActualTime());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart find(int id) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT actual_time, bet, possible_win, total_odds FROM cart WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Date actualTime = rs.getDate(1);
            float bet = rs.getFloat(2);
            int possibleWin = rs.getInt(3);
            float totalOdds = rs.getInt(4);
            Cart result = new Cart("DescriptionTODO?");
            result.setActualTime(actualTime);
            result.setBet(bet);
            result.setPossibleWin(possibleWin);
            result.setTotalOdds(totalOdds);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Cart Database Error!");
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM cart WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> getAll() {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT id, actual_time, bet, possible_win, total_odds FROM cart";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Cart> result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date actualTime = rs.getDate(2);
                float bet = rs.getFloat(3);
                int possibleWin = rs.getInt(4);
                float totalOdds = rs.getInt(5);
                Cart cart = new Cart("DescriptionTODO?");
                cart.setId(id);
                cart.setActualTime(actualTime);
                cart.setBet(bet);
                cart.setPossibleWin(possibleWin);
                cart.setTotalOdds(totalOdds);
                result.add(cart);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Cart Database Error!");
        }
    }
//TODO this will be required

//    @Override
//    public void update(Cart cart) {
//        try (Connection conn = dataSource.getConnection()) {
//            String sql = "UPDATE cart " +
//                    "SET actual_time = ?, bet = ?, possible_win = ?, total_odds = ?" +
//                    "WHERE id = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setDate(1, (Date) cart.getActualTime());
//            statement.setFloat(2, cart.getBet());
//            statement.setInt(3, cart.getPossibleWin());
//            statement.setFloat(4, cart.getTotalOdds());
//            statement.setInt(5, cart.getId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}






