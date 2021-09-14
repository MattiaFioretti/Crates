package it.matty.crate.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IConnectionManager {

    void close(Connection connection, ResultSet resultSet, PreparedStatement... statements) throws SQLException;

    Connection getConnection() throws SQLException;

}
