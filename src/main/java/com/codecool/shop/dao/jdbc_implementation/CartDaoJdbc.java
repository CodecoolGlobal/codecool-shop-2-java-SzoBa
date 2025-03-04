package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CartItemDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJdbc implements CartDao {
    private final DataSource dataSource;
    private CartItemDao cartItemDao;


    public CartDaoJdbc(DataSource dataSource, CartItemDao cartItemDao) {
        this.dataSource = dataSource;
        this.cartItemDao = cartItemDao;

    }


    @Override
    public void add(Cart cart) {
    }

    @Override
    public void add(Cart cart, int clientSessionIdHashCode) {
        cart.setId(clientSessionIdHashCode);
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart" +
                    "(id)" +
                    "VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            statement.setInt(1, cart.getId());
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
            result.setId(id);
            List<CartItem> items = cartItemDao.getAllByCart(id);
            items.forEach(result::addItemToCart);
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
//            cartItemDao.removeAll(id); - TODO: check if CASCADE works!!!
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override //TODO it is not loading the items into the cart currently!!!
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
                Cart cart = new Cart("Description");
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

    @Override
    public void update(Cart cart) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE cart " +
                    "SET bet = ?, possible_win = ?, total_odds = ?" +
                    "WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setDate(1, (Date) cart.getActualTime());
            statement.setFloat(1, cart.getBet());
            statement.setInt(2, cart.getPossibleWin());
            statement.setFloat(3, cart.getTotalOdds());
            statement.setInt(4, cart.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}






