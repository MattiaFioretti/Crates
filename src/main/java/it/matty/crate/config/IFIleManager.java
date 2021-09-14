package it.matty.crate.config;

import it.matty.crate.config.objects.IConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Set;

public interface IFIleManager {

    IConfigFile getFile(String name);

    Set<IConfigFile> getFiles();

    YamlConfiguration getConfig(String name);

    void addFile(IConfigFile file);

    void removeFile(String name);
}
