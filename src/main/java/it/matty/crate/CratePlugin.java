package it.matty.crate;

import it.matty.crate.commands.CrateCommand;
import it.matty.crate.config.FileManager;
import it.matty.crate.config.IFIleManager;
import it.matty.crate.crates.CrateManager;
import it.matty.crate.crates.ICrateManager;
import it.matty.crate.database.ConnectionManager;
import it.matty.crate.database.IConnectionManager;
import it.matty.crate.listeners.CrateListener;
import it.matty.crate.listeners.InventoryListener;
import it.matty.crate.listeners.PlayerListener;
import it.matty.crate.users.IUserManager;
import it.matty.crate.users.UserManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public class CratePlugin extends JavaPlugin {
    @Getter
    private static CratePlugin plugin;

    private ICrateManager crateManager;
    private IUserManager userManager;

    private IFIleManager fileManager;
    private IConnectionManager connectionManager;

    @Override
    public void onLoad() {
        plugin = this;

        this.crateManager = new CrateManager();
        this.userManager = new UserManager();
        this.fileManager = new FileManager();

        this.connectionManager = new ConnectionManager("database");
    }

    @Override
    @SneakyThrows
    public void onEnable() {
        saveDefaultConfig();

        connectionManager.connect();
        crateManager.loadCrates();

        new CrateCommand(this, "crate");

        new CrateListener(this);
        new PlayerListener(this);
        new InventoryListener(this);
    }

    @Override
    public void onDisable() {
        connectionManager.disconnect();
    }
}
