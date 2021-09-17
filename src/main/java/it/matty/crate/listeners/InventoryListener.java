package it.matty.crate.listeners;

import com.sun.org.apache.regexp.internal.RE;
import it.matty.crate.CratePlugin;
import it.matty.crate.crates.cratekey.Crate;
import it.matty.crate.crates.rewards.CrateReward;
import it.matty.crate.listeners.manager.ListenerManager;
import it.matty.crate.messages.Message;
import it.matty.crate.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryListener extends ListenerManager {

    public InventoryListener(CratePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!getPlugin().getCrateManager().getEditorManager().isEditing(player)) return;
        if (event.getCurrentItem() == null) return;

        if (!(event.getClickedInventory() instanceof PlayerInventory)) return;
        player.getOpenInventory().getTopInventory().addItem(new ItemBuilder(event.getCurrentItem())
                .setLore("", "&7Percentage: &f0 %", "", "&eRIGHT CLICK &7+1 Percentage",
                        "&eLEFT CLICK &7-1 Percentage", "&eMIDDLE CLICK &7Remove Item").build());

        event.setCancelled(true);
    }

    @EventHandler
    public void onModify(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!getPlugin().getCrateManager().getEditorManager().isEditing(player)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getClickedInventory() instanceof PlayerInventory) return;

        event.setCancelled(true);

        switch (event.getClick()) {
            case MIDDLE:
                event.getCurrentItem().setAmount(0);
                break;
            case RIGHT:
                if(getPercentage(event.getCurrentItem()) == 100) return;
                event.getInventory().setItem(event.getSlot(), new ItemBuilder(event.getCurrentItem())
                        .setLore("", "&7Percentage: &f" + (getPercentage(event.getCurrentItem()) + 1) + " %", "", "&eRIGHT CLICK &7+1 Percentage",
                                "&eLEFT CLICK &7-1 Percentage", "&eMIDDLE CLICK &7Remove Item").build());
                break;
            case LEFT:
                if(getPercentage(event.getCurrentItem()) < 1) return;
                event.getInventory().setItem(event.getSlot(), new ItemBuilder(event.getCurrentItem())
                        .setLore("", "&7Percentage: &f" + (getPercentage(event.getCurrentItem()) - 1) + " %", "", "&eRIGHT CLICK &7+1 Percentage",
                                "&eLEFT CLICK &7-1 Percentage", "&eMIDDLE CLICK &7Remove Item").build());
                break;
        }
    }


    @EventHandler
    public void onClick(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!getPlugin().getCrateManager().getEditorManager().isEditing(player)) return;
        Crate crate = getPlugin().getCrateManager().getEditorManager().getEditor(player).getCrate();
        crate.getItems().clear();

        for (ItemStack itemStack : event.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            crate.addItem(new CrateReward(itemStack, getPercentage(itemStack)));
        }

        getPlugin().getCrateManager().getEditorManager().removeEditor(player);
        getPlugin().getCrateManager().saveCrate(crate);

        Message.CRATE_SAVED.send(player);
    }

    public double getPercentage(ItemStack itemStack) {
        return Double.parseDouble(ChatColor.stripColor(itemStack.getItemMeta().getLore().get(1)).split(" ")[1]);
    }
}
