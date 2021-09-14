package it.matty.crate.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.matty.crate.CratePlugin;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionManager implements IConnectionManager {
    @Getter private final HikariDataSource dataSource;

    public ConnectionManager(ConfigurationSection section) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(String.format("jdbc:mysql://%s/%s", section.getString("hostname"), section.getString("database")));
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(section.getString("username"));
        config.setPassword(section.getString("password"));
        config.setPoolName(CratePlugin.getPlugin().getName());

        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public void close(Connection conn, ResultSet rs, PreparedStatement... stmt) throws SQLException {
        Objects.requireNonNull(conn).close();
        Objects.requireNonNull(rs).close();

        for(PreparedStatement statement : stmt) {
            Objects.requireNonNull(statement).close();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
