package it.matty.crate.crates.cratekey;

import it.matty.crate.crates.rewards.Reward;
import it.matty.crate.users.objects.CrateUser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface Crate {

    ItemStack getCrate();

    Set<Reward> getItems();

    String getName();

    void addItem(Reward item);

    void removeItem(Reward item);

    void open(CrateUser player);
}
