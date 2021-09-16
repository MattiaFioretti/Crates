package it.matty.crate.crates;

import it.matty.crate.crates.cratekey.Crate;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CrateManager implements ICrateManager {
    @Getter private final Set<Crate> crates = new HashSet<>();

    @Override
    public Crate getCrate(String name) {
        for(Crate crate : crates) {
            if(crate.getName().equalsIgnoreCase(name))
                return crate;
        }
        return null;
    }

    @Override
    public Crate getCrate(ItemStack itemStack) {
        for(Crate crate : crates) {
            if(crate.getCrate().isSimilar(itemStack)) {
                if(crate.getCrate() == null) continue;
                return crate;
            }
        }
        return null;
    }

    @Override
    public void addCrate(Crate crate) {
        crates.add(crate);
    }

    @Override
    public void removeCrate(String crate) {
        crates.remove(getCrate(crate));
    }

    @Override
    public void open(Crate crate, Player player) {
        crate.open(player);
    }
}
