package it.matty.crate.crates;

import com.sun.istack.internal.Nullable;
import it.matty.crate.crates.cratekey.Crate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface ICrateManager {

    Set<Crate> getCrates();

    @Nullable Crate getCrate(String name);

    @Nullable Crate getCrate(ItemStack itemStack);

    void addCrate(Crate crate);

    void removeCrate(String crate);

    void open(Crate crate, Player player);
}
