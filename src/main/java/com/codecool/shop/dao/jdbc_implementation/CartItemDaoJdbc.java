package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.CartItemDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.model.CartItem;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDaoJdbc implements CartItemDao {
    private final DataSource dataSource;
    private MatchDetailsDao matchDetailsDao;

    public CartItemDaoJdbc(DataSource dataSource, MatchDetailsDao matchDetailsDao) {
        this.dataSource = dataSource;
        this.matchDetailsDao = matchDetailsDao;
    }


    @Override
    public void add(CartItem cartItem, int cartId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart_item" +
                    "(chosen_team, odds, match_id)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            statement.setString(1, cartItem.getChosenOutcome());
            statement.setFloat(2, cartItem.getOdds());
            statement.setInt(3, cartId);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CartItem find(int cartId, int matchId) {
        List<CartItem> cardItems = getAllByCart(cartId);
        return cardItems.stream().filter(item->(item.getMatchId() == matchId)).findFirst().orElse(null);
    }

    @Override
    public void remove(int cartId, int matchId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM cart_item WHERE match_id = ? AND cart_id= ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, matchId);
            statement.setInt(2, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAll(int cartId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM cart_item WHERE cart_id= ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<CartItem> getAllByCart(int cartId) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT chosen_team, match_id FROM cart_item WHERE cart_id=?";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<CartItem> result = new ArrayList<>();
            while (rs.next()) {
                String chosenTeam = rs.getString(1);
                int matchId = rs.getInt(2);
                CartItem cartItem = new CartItem("DescriptionTODO", matchDetailsDao.find(matchId), chosenTeam);
                result.add(cartItem);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Cart Database Error!");
        }

    }
}
