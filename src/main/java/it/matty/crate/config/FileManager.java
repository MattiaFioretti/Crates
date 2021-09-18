package it.matty.crate.config;

import it.matty.crate.CratePlugin;
import it.matty.crate.config.objects.ConfigFile;
import it.matty.crate.config.objects.DefaultConfigFile;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
import java.util.Set;

public class FileManager implements IFIleManager {
    @Getter
    private final Set<ConfigFile> files = new HashSet<>();

    public FileManager() {
        addFile(new DefaultConfigFile(CratePlugin.getPlugin(), "crates"));
        addFile(new DefaultConfigFile(CratePlugin.getPlugin(), "messages"));
    }

    @Override
    public ConfigFile getFile(String name) {
        for (ConfigFile file : files) {
            if (file.getName().equalsIgnoreCase(name))
                return file;
        }
        return null;
    }

    @Override
    public YamlConfiguration getConfig(String name) {
        return getFile(name).getConfig();
    }

    @Override
    public void addFile(ConfigFile file) {
        files.add(file);
    }

    @Override
    public void removeFile(String name) {
        files.remove(getFile(name));
    }
}