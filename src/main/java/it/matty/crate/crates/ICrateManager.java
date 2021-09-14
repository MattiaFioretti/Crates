package it.matty.crate.crates;

import it.matty.crate.crates.crate.Crate;
import org.bukkit.entity.Player;

import java.util.Set;

public interface ICrateManager {

    Set<Crate> getCrates();

    Crate getCrate(String name);

    void addCrate(Crate crate);

    void removeCrate(String crate);

    void open(Crate crate, Player player);
}
