package it.matty.crate.listeners.manager;

import it.matty.crate.CratePlugin;
import lombok.Getter;
import org.bukkit.event.Listener;

@Getter
public class ListenerManager implements Listener {
    private final CratePlugin plugin;

    public ListenerManager(CratePlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
