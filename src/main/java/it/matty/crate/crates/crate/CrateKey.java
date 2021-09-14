package it.matty.crate.crates.crate;

import it.matty.crate.crates.rewards.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class CrateKey implements Crate {
    @Getter private final String name;
    @Getter private final ItemStack crate;
    @Getter private final Set<Reward> items = new HashSet<>();

    @Override
    public void addItem(Reward item) {
        this.items.add(item);
    }

    @Override
    public void removeItem(Reward item) {
        this.items.remove(item);
    }
}
