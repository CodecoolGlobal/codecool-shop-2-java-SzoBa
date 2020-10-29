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
    void add_SQLException_ThrowsRuntimeException() throws SQLException {
        Mockito.when(dataSource.getConnection()).thenThrow(SQLException.class);
        assertThrows(RuntimeException.class, () -> sportTypeDaoJdbc.add(new SportType("Handball", "Handball description")));
    }


    @Test
    void add_addItem_ReturnsProperId() throws SQLException {
        SportType testSport = new SportType("Handball", "Handball description");
        Mockito.when(testConnection.prepareStatement(Mockito.anyString(), Mockito.anyInt())).thenReturn(testStatement);
        Mockito.when(testStatement.getGeneratedKeys()).thenReturn(testResultSet);
        Mockito.when(testResultSet.getInt(1)).thenReturn(1001);
        testSport.setId(1001);
        sportTypeDaoJdbc.add(testSport);
        assertEquals(1001, testResultSet.getInt(1));
    }


    @Test
    void find_searchForExistingId_returnsProperObject() throws SQLException {
        SportType testSportType = new SportType ("Football", "description");
        testSportType.setId(1);
        Mockito.when(testConnection.prepareStatement(Mockito.anyString())).thenReturn(testStatement);
        Mockito.when(testStatement.executeQuery()).thenReturn(testResultSet);
        Mockito.when(testResultSet.getString("name")).thenReturn("TheName");
        assertEquals(testSportType.getName(), sportTypeDaoJdbc.find(1).getName());
    }

    @Test
    void remove() throws SQLException {
        SportType testSport1 = new SportType("Handball", "Handball description");
        SportType testSport2 = new SportType("Football", "Football description");
        Mockito.when(testConnection.prepareStatement(Mockito.anyString(), Mockito.anyInt())).thenReturn(testStatement);
        Mockito.when(testConnection.prepareStatement(Mockito.anyString())).thenReturn(testStatement);
//        Mockito.when(testStatement.getGeneratedKeys()).thenReturn(testResultSet);
        Mockito.when(sportTypeDaoJdbc.getAll().size()).thenReturn(1);

        sportTypeDaoJdbc.add(testSport1);
        testSport1.setId(1);
        sportTypeDaoJdbc.add(testSport2);
        testSport2.setId(2);

        sportTypeDaoJdbc.remove(2);
        assertEquals(1, sportTypeDaoJdbc.getAll().size());
    }

    @Test
    void getAll() throws SQLException {
        Mockito.when(testConnection.prepareStatement(Mockito.anyString())).thenReturn(testStatement);
        Mockito.when(testConnection.createStatement()).thenReturn(testStatement);
        Mockito.when(testStatement.executeQuery(Mockito.anyString())).thenReturn(testResultSet);

        Mockito.when(testResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(testResultSet.getInt(Mockito.anyInt())).thenReturn(1).thenReturn(2);
        Mockito.when(testResultSet.getString("name")).thenReturn("Name1").thenReturn("Name2");

        assertEquals(2, sportTypeDaoJdbc.getAll().size());
    }
}