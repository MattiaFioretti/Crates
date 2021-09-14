package it.matty.crate.config.objects;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public interface IConfigFile {

    YamlConfiguration getConfig();

    File getFile();

    String getName();

    void save() throws IOException;

    void reload();

    default void saveAndReload() throws IOException {
        save();
        reload();
    }
}