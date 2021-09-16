package it.matty.crate.database;

import it.matty.crate.database.data.DataManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IConnectionManager {

    void close(Connection connection, ResultSet resultSet, PreparedStatement... statements);

    void closeConnection();

    void start() throws SQLException;

    Connection getConnection() throws SQLException;

    DataManager getDataManager();
}
