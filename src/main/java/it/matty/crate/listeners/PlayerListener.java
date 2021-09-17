package it.matty.crate.listeners;

import it.matty.crate.CratePlugin;
import it.matty.crate.listeners.manager.ListenerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener extends ListenerManager {

    public PlayerListener(CratePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getPlugin().getConnectionManager().getDataManager().registerUser(player).whenComplete((cratePlayer, e) -> {
            getPlugin().getUserManager().addUser(cratePlayer);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        getPlugin().getConnectionManager().getDataManager().saveUser(player);
    }
}
