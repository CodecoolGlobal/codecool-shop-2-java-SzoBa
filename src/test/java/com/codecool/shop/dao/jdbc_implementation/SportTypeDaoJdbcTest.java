package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.model.SportType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class SportTypeDaoJdbcTest {

    private DataSource dataSource;
    private SportTypeDaoJdbc sportTypeDaoJdbc;

    private Connection testConnection;
    private PreparedStatement testStatement;
    private ResultSet testResultSet;

    @Mock
    private ResultSet rs;

    private SportType sportType;

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