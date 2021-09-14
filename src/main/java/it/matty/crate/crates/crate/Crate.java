package it.matty.crate.crates.crate;

import it.matty.crate.crates.rewards.Reward;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface Crate {

    ItemStack getCrate();

    Set<Reward> getItems();

    String getName();

    void addItem(Reward item);

    void removeItem(Reward item);

    void open(Player player);
}
