package it.matty.crate.crates.rewards;

import org.bukkit.inventory.ItemStack;


public interface Reward {

    double getChance();

    ItemStack getItem();

    void setItem(ItemStack item);

    void setChange(double chance);
}

