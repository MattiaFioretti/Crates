package it.matty.crate.crates.crates;

import it.matty.crate.crates.rewards.Reward;
import it.matty.crate.users.objects.CrateUser;
import it.matty.crate.users.objects.DefaultCrateUser;
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
