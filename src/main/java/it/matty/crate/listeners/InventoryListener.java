package it.matty.crate.listeners;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.crates.editor.sessions.EditSession;
import it.matty.crate.crates.rewards.DefaultReward;
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
                .setLore("", "&7Percentage: &f0 %", "", "&eSHIFT CLICK &7Set Percentage", "&eMIDDLE CLICK &7Remove Item").build());

        event.setCancelled(true);
    }

    @EventHandler
    public void onModify(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        EditSession session = getPlugin().getCrateManager().getEditorManager().getEditor(player);

        assert session != null;

        if (!getPlugin().getCrateManager().getEditorManager().isEditing(player)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getClickedInventory() instanceof PlayerInventory) return;

        event.setCancelled(true);

        switch (event.getClick()) {
            case MIDDLE:
                event.getCurrentItem().setAmount(0);
                break;
            case SHIFT_RIGHT:
                session.setItem(event.getCurrentItem());
                event.getInventory().setItem(event.getSlot(), null);
                player.closeInventory();

                Message.SET_PERCENTAGE.send(player);
                break;
        }
    }


    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        EditSession session = getPlugin().getCrateManager().getEditorManager().getEditor(player);

        if (!getPlugin().getCrateManager().getEditorManager().isEditing(player)) return;
        Crate crate = getPlugin().getCrateManager().getEditorManager().getEditor(player).getCrate();
        crate.getItems().clear();

        for (ItemStack itemStack : event.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            crate.addItem(new DefaultReward(itemStack, getPercentage(itemStack)));
        }

        getPlugin().getCrateManager().saveCrate(crate);

        if(session.getItem() == null) {
            getPlugin().getCrateManager().getEditorManager().removeEditor(player);
            Message.CRATE_SAVED.send(player);
        }
    }

    public double getPercentage(ItemStack itemStack) {
        return Double.parseDouble(ChatColor.stripColor(itemStack.getItemMeta().getLore().get(1)).split(" ")[1]);
    }
}
