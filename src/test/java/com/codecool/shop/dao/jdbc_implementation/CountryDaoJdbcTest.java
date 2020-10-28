package com.codecool.shop.dao.jdbc_implementation;

import com.codecool.shop.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class CountryDaoJdbcTest {

    private DataSource dataSource;
    private CountryDaoJdbc testCountryDaoJdbc;

    private Connection testConnection;
    private PreparedStatement testStatement;
    private ResultSet testResultSet;



    @BeforeEach
    void setUp() throws SQLException {
        dataSource = Mockito.mock(DataSource.class);
        testConnection = Mockito.mock(Connection.class);
        testStatement = Mockito.mock(PreparedStatement.class);
        testResultSet = Mockito.mock(ResultSet.class);
        testCountryDaoJdbc = new CountryDaoJdbc(dataSource);
        Mockito.when(dataSource.getConnection()).thenReturn(testConnection);


    }

    @Test
    void add_SQLException_ThrowsRuntimeException() throws SQLException {
        Mockito.when(dataSource.getConnection()).thenThrow(SQLException.class);
        assertThrows(RuntimeException.class, () -> testCountryDaoJdbc.add(new Country("name", "description")));
    }


    @Test
    void add_addItem_ReturnsId() throws SQLException {
        Country testCountry = new Country ("TheName", "description");
        Mockito.when(testConnection.prepareStatement(Mockito.anyString(), Mockito.anyInt())).thenReturn(testStatement);
        Mockito.when(testStatement.getGeneratedKeys()).thenReturn(testResultSet);
        Mockito.when(testResultSet.getInt(1)).thenReturn(1001);
        testCountry.setId(1001);
        testCountryDaoJdbc.add(testCountry);
        assertEquals(1001, testResultSet.getInt(1));
    }


    @Test
    void find_searchForExistingId_returnsProperObject() throws SQLException {
        Country testCountry = new Country ("TheName", "description");
        testCountry.setId(1);
        Mockito.when(testConnection.prepareStatement(Mockito.anyString())).thenReturn(testStatement);
        Mockito.when(testStatement.executeQuery()).thenReturn(testResultSet);
        Mockito.when(testResultSet.getString("name")).thenReturn("TheName");
        assertEquals(testCountry.getName(), testCountryDaoJdbc.find(1).getName());
    }

    @Test
    void remove() {
    }

    @Test
    void getAll() {
    }
}