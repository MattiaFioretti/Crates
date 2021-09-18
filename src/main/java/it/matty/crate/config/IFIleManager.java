package it.matty.crate.config;

import it.matty.crate.config.objects.ConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Set;

public interface IFIleManager {

    ConfigFile getFile(String name);

    Set<ConfigFile> getFiles();

    YamlConfiguration getConfig(String name);

    void addFile(ConfigFile file);

    void removeFile(String name);
}
