package it.matty.crate.database;

import it.matty.crate.database.service.SQLUsersManager;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionManager {

    void disconnect();

    void connect() throws SQLException;

    Connection getConnection() throws SQLException;

    SQLUsersManager getDataManager();
}
