package it.matty.crate.database.data;

import it.matty.crate.CratePlugin;
import it.matty.crate.database.IConnectionManager;
import it.matty.crate.users.objects.CrateUser;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class DataManager {
    private final Executor exe = (command) -> Bukkit.getScheduler().runTaskAsynchronously(CratePlugin.getPlugin(), command);
    private final IConnectionManager manager;

    @SneakyThrows
    public DataManager(IConnectionManager manager) {
        this.manager = manager;
    }

    public CompletableFuture<CrateUser> registerUser(Player player) {
        return CompletableFuture.supplyAsync(() -> {

            Connection connection = null;
            PreparedStatement checkStmt = null;
            PreparedStatement addStmt = null;
            ResultSet resultSet = null;

            try {
                connection = manager.getConnection();

                checkStmt = connection.prepareStatement("SELECT * FROM players WHERE UUID=?");
                checkStmt.setString(1, player.getUniqueId().toString());
                resultSet = checkStmt.executeQuery();

                if (!resultSet.next()) {
                    addStmt = connection.prepareStatement("INSERT INTO players(UUID) values (?)");
                    addStmt.setString(1, player.getUniqueId().toString());
                    addStmt.executeUpdate();
                    return new CrateUser(player.getUniqueId(), 0);
                } else {
                    return new CrateUser(player.getUniqueId(), resultSet.getInt("crates"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                manager.close(connection, resultSet, checkStmt, addStmt);
            }
            return null;
        }, exe);
    }

    public void saveUser(Player player) {
        CrateUser user = CratePlugin.getPlugin().getUserManager().getUser(player);
    }
}