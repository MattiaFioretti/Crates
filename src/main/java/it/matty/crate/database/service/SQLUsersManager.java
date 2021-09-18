package it.matty.crate.database.service;

import it.matty.crate.CratePlugin;
import it.matty.crate.database.IConnectionManager;
import it.matty.crate.users.objects.CrateUser;
import it.matty.crate.users.objects.DefaultCrateUser;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SQLUsersManager {
    private final Executor exe = (command) -> Bukkit.getScheduler().runTaskAsynchronously(CratePlugin.getPlugin(), command);
    private final IConnectionManager manager;

    @SneakyThrows
    public SQLUsersManager(IConnectionManager manager) {
        this.manager = manager;
    }

    public CompletableFuture<DefaultCrateUser> registerUser(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = manager.getConnection();
                 PreparedStatement addStmt = connection.prepareStatement("INSERT INTO players(UUID) values (?)");
                 PreparedStatement checkStmt = connection.prepareStatement("SELECT * FROM players WHERE UUID=?")) {
                checkStmt.setString(1, player.getUniqueId().toString());
                try (ResultSet set = checkStmt.executeQuery()) {
                    if (!set.next()) {
                        addStmt.setString(1, player.getUniqueId().toString());
                        addStmt.executeUpdate();
                        return new DefaultCrateUser(player.getUniqueId());
                    } else {
                        DefaultCrateUser user = new DefaultCrateUser(player.getUniqueId());
                        user.setCrates(set.getInt("crates"));
                        return user;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, exe);
    }

    public void saveUser(Player player) {
        CompletableFuture.runAsync(() -> {
            CrateUser user = CratePlugin.getPlugin().getUserManager().getUser(player);

            try (Connection connection = manager.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE players SET crates=? WHERE UUID=?")) {
                statement.setInt(1, user.getCrates());
                statement.setString(2, user.getUuid().toString());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, exe);
    }
}