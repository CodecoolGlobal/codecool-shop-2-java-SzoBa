package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.model.SportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class SportTypeDaoJdbcTest {

    private DataSource dataSource;
    private SportTypeDaoJdbc sportTypeDaoJdbc;

    private Connection testConnection;
    private PreparedStatement testStatement;
    private ResultSet testResultSet;



    @BeforeEach
    void setUp() throws SQLException {
        dataSource = Mockito.mock(DataSource.class);
        testConnection = Mockito.mock(Connection.class);
        testStatement = Mockito.mock(PreparedStatement.class);
        testResultSet = Mockito.mock(ResultSet.class);
        sportTypeDaoJdbc = new SportTypeDaoJdbc(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(testConnection);
    }

    @Test
    void add() {

    }

    @Test
    void find() {
    }

    @Test
    void remove() {
    }

    @Test
    void getAll() {
    }
}