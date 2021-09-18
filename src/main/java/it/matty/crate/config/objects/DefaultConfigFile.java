package it.matty.crate.config.objects;

import it.matty.crate.CratePlugin;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DefaultConfigFile implements ConfigFile {
    @Getter
    private final String name;
    @Getter
    private final File file;
    @Getter
    private YamlConfiguration config;

    @SneakyThrows
    public DefaultConfigFile(CratePlugin plugin, String name) {
        this.name = name;

        this.file = new File(plugin.getDataFolder(), name + ".yml");
        if (!file.exists()) CratePlugin.getPlugin().saveResource(name + ".yml", true);
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
