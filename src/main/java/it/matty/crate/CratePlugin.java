package it.matty.crate;

import it.matty.crate.commands.CrateCommand;
import it.matty.crate.config.FileManager;
import it.matty.crate.config.IFIleManager;
import it.matty.crate.crates.CrateManager;
import it.matty.crate.crates.ICrateManager;
import it.matty.crate.crates.cratekey.CrateKey;
import it.matty.crate.database.ConnectionManager;
import it.matty.crate.database.IConnectionManager;
import it.matty.crate.listeners.PlayerListener;
import it.matty.crate.users.IUserManager;
import it.matty.crate.users.UserManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CratePlugin extends JavaPlugin {
    @Getter private static CratePlugin plugin;

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
    }

    @Override @SneakyThrows
    public void onEnable() {
        saveDefaultConfig();

        this.connectionManager = new ConnectionManager("database");
        connectionManager.start();

        crateManager.addCrate(new CrateKey("Test", null));

        new CrateCommand(this, "crate");
        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
        connectionManager.closeConnection();
    }
}
