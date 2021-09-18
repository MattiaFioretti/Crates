package it.matty.crate.crates.crates;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.rewards.Reward;
import it.matty.crate.messages.Message;
import it.matty.crate.users.objects.CrateUser;
import it.matty.crate.utils.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class DefaultCrate implements Crate {
    @Getter
    private final String name;
    @Getter
    private final ItemStack crate;
    @Getter
    private final Set<Reward> items;

    @Override
    public void addItem(Reward item) {
        this.items.add(item);
    }

    @Override
    public void removeItem(Reward item) {
        this.items.remove(item);
    }

    @Override
    public void open(CrateUser user) {
        List<String> materials = CratePlugin.getPlugin().getConfig().getStringList("settings.animation");
        Player player = Bukkit.getPlayer(user.getUuid());

        assert player != null;

        Inventory inventory = Bukkit.createInventory(null, 45, Message.OPENING.get());
        player.openInventory(inventory);

        AtomicInteger on = new AtomicInteger();
        AtomicInteger under = new AtomicInteger(44);

        new BukkitRunnable() {
            @Override
            public void run() {
                inventory.setItem(on.getAndIncrement(), new ItemStack(Material.valueOf(materials.get(ThreadLocalRandom.current().nextInt(materials.size())))));
                inventory.setItem(under.getAndDecrement(), new ItemStack(Material.valueOf(materials.get(ThreadLocalRandom.current().nextInt(materials.size())))));

                if (on.get() == 22) {
                    Reward reward = getRandomReward();
                    if(reward != null) {
                        inventory.setItem(22, reward.getItem());
                        player.getInventory().addItem(reward.getItem());
                    } else inventory.setItem(22, new ItemBuilder(Material.BARRIER).build());

                    user.setOpening(false);
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(CratePlugin.getPlugin(), 10, 10);
    }

    public Reward getRandomReward() {
        double chance = new Random().nextDouble() * 100D;
        double cumulative = 0.0;
        for (Reward prize : items) {
            cumulative += prize.getChance();
            if (chance < cumulative)
                return prize;
        }
        return null;
    }
}
