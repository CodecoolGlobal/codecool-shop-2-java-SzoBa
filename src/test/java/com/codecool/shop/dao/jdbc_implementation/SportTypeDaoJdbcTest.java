package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.dao.implementation.SportTypeDaoMem;
import com.codecool.shop.model.SportType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SportTypeDaoJdbcTest {

    @Mock
    private DataSource ds;

    @Mock
    private Connection c;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    private SportType sportType;

    @BeforeAll
    void setup() throws SQLException {
        assertNotNull(ds);
        when(c.prepareStatement(any(String.class))).thenReturn(stmt);
        when(ds.getConnection()).thenReturn(c);

        sportType = new SportType("Football", "Football description");

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