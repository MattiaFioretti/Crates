package it.matty.crate.config.objects;

import it.matty.crate.CratePlugin;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile implements IConfigFile {
    @Getter private final String name;
    @Getter private final File file;
    @Getter private YamlConfiguration config;

    public ConfigFile(CratePlugin plugin, String name) {
        this.name = name;

        this.file = new File(plugin.getDataFolder(), name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void save() throws IOException {
        config.save(file);
    }

    @Override
    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
}
