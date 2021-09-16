package it.matty.crate.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.matty.crate.CratePlugin;
import it.matty.crate.database.data.DataManager;
import it.matty.crate.database.enums.Tables;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionManager implements IConnectionManager {
    @Getter
    private final HikariDataSource dataSource;

    @Getter
    private final DataManager dataManager;

    public ConnectionManager(String sect) {
        ConfigurationSection section = CratePlugin.getPlugin().getConfig().getConfigurationSection(sect);
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(String.format("jdbc:mysql://%s/%s", section.getString("hostname"), section.getString("database")));
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(section.getString("username"));
        config.setPassword(section.getString("password"));

        this.dataSource = new HikariDataSource(config);
        this.dataManager = new DataManager(this);
    }

    @Override @SneakyThrows
    public void close(Connection conn, ResultSet rs, PreparedStatement... stmt) {
        if (conn != null) conn.close();
        if (rs != null) rs.close();

        for (PreparedStatement statement : stmt)
            if (statement != null) statement.close();
    }

    @Override @SneakyThrows
    public Connection getConnection() {
        return dataSource.getConnection();
    }

    @Override
    public void closeConnection() {
        dataSource.close();
    }

    @Override
    public void start() {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Tables.PLAYERS.getTable());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            close(connection, null, statement);
        }
    }
}
