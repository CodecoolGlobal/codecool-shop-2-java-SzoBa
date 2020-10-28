package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabaseManager {
    private static CartDao cartDao;
    private static CartItemDao cartItemDao;
    private static CountryDao countryDao;
    private static MatchDetailsDao matchDetailsDao;
    private static OrderDao orderDao;
    private static SportTypeDao sportTypeDao;

    private static GameDatabaseManager instance = null;
    public static GameDatabaseManager getInstance() {
        if (instance == null) {
            instance = new GameDatabaseManager();
            setup();
        }
        return instance;
    }

    private static void setup() {
        try {
            DataSource dataSource = connect();
            countryDao = new CountryDaoJdbc(dataSource);
            sportTypeDao = new SportTypeDaoJdbc(dataSource);
            matchDetailsDao = new MatchDetailsDaoJdbc(dataSource, countryDao, sportTypeDao);
            cartItemDao = new CartItemDaoJdbc(dataSource, matchDetailsDao);
            cartDao = new CartDaoJdbc(dataSource, cartItemDao);
            orderDao = new OrderDaoJdbc(dataSource);
        } catch (SQLException e) {
            System.out.println("Database connection error!");
        }
    }

    private static DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(System.getenv("PSQL_DB"));
        dataSource.setUser(System.getenv("PSQL_USER"));
        dataSource.setPassword(System.getenv("PSQL_PASSWORD"));

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public CartItemDao getCartItemDao() {
        return cartItemDao;
    }

    public CountryDao getCountryDao() {
        return countryDao;
    }

    public MatchDetailsDao getMatchDetailsDao() {
        return matchDetailsDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public SportTypeDao getSportTypeDao() {
        return sportTypeDao;
    }
}
