package it.matty.crate;

import it.matty.crate.commands.CrateCommand;
import it.matty.crate.config.FileManager;
import it.matty.crate.config.IFIleManager;
import it.matty.crate.crates.CrateManager;
import it.matty.crate.crates.ICrateManager;
import it.matty.crate.database.ConnectionManager;
import it.matty.crate.database.IConnectionManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CratePlugin extends JavaPlugin {
    @Getter
    private static CratePlugin plugin;

    @Getter
    private ICrateManager crateManager;

    @Getter
    private IFIleManager fileManager;

    @Getter
    private IConnectionManager connectionManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        this.crateManager = new CrateManager();
        this.fileManager = new FileManager();
        this.connectionManager = new ConnectionManager(getConfig().getConfigurationSection("database"));

        new CrateCommand(this, "crate");
    }
}
