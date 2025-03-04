package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoJdbcTest {

    private DataSource dataSource;
    private OrderDaoJdbc testOrderDaoJdbc;

    private Connection testConnection;
    private PreparedStatement testStatement;
    private ResultSet testResultSet;
    private Order testOrder;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = Mockito.mock(DataSource.class);
        testConnection = Mockito.mock(Connection.class);
        testStatement = Mockito.mock(PreparedStatement.class);
        testResultSet = Mockito.mock(ResultSet.class);
        testOrderDaoJdbc = new OrderDaoJdbc(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(testConnection);
        testOrder = new Order ("description", "Kevin", "Johnson", "06705551223", "idunno@gamil.com", "Hungary",
                "Budapest", 1011, "123. Sesame Street");
    }

    @Test
    void add_executeQuery_throwsSQLException() throws SQLException {

        Mockito.when(testConnection.prepareStatement(Mockito.anyString())).thenReturn(testStatement);
        Mockito.when(testStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(RuntimeException.class, ()->testOrderDaoJdbc.add(testOrder));
    }


    @Test
    void find_executeQuery_throwsSQLException() throws SQLException {
        Mockito.when(testConnection.prepareStatement(Mockito.anyString())).thenReturn(testStatement);
        Mockito.when(testStatement.executeQuery()).thenReturn(testResultSet);
        Mockito.when(testResultSet.next()).thenThrow(SQLException.class);
        assertThrows(RuntimeException.class, ()-> testOrderDaoJdbc.find(1));
    }
}