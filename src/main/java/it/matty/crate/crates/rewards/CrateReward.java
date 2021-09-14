package it.matty.crate.crates.rewards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class CrateReward implements Reward {
    @Getter private ItemStack item;
    @Getter private double chance;

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setChange(double chance) {
        this.chance = chance;
    }
}
