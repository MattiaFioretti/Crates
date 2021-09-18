package it.matty.crate.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.matty.crate.CratePlugin;
import it.matty.crate.database.service.SQLUsersManager;
import it.matty.crate.database.utils.Tables;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionManager implements IConnectionManager {
    @Getter
    private final HikariDataSource dataSource;

    @Getter
    private final SQLUsersManager dataManager;

    public ConnectionManager(String sect) {
        ConfigurationSection section = CratePlugin.getPlugin().getConfig().getConfigurationSection(sect);
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(String.format("jdbc:mysql://%s/%s", section.getString("hostname"), section.getString("database")));
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(section.getString("username"));
        config.setPassword(section.getString("password"));

        this.dataSource = new HikariDataSource(config);
        this.dataManager = new SQLUsersManager(this);
    }

    @Override
    @SneakyThrows
    public Connection getConnection() {
        return dataSource.getConnection();
    }

    @Override
    public void disconnect() {
        dataSource.close();
    }

    @Override
    public void connect() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(Tables.PLAYERS.getTable())) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
