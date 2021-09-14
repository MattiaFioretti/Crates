package it.matty.crate;

import it.matty.crate.commands.CrateCommand;
import it.matty.crate.crates.CrateManager;
import it.matty.crate.crates.ICrateManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CratePlugin extends JavaPlugin {
    @Getter
    private static CratePlugin plugin;

    @Getter
    private ICrateManager crateManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        this.crateManager = new CrateManager();

        new CrateCommand(this, "crate");
    }
}
