package it.matty.crate.listeners;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.crates.editor.sessions.EditSession;
import it.matty.crate.crates.rewards.Reward;
import it.matty.crate.listeners.manager.ListenerManager;
import it.matty.crate.messages.Message;
import it.matty.crate.messages.builder.MessageBuilder;
import it.matty.crate.users.IUserManager;
import it.matty.crate.users.objects.CrateUser;
import it.matty.crate.utils.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class CrateListener extends ListenerManager {
    private final IUserManager manager = getPlugin().getUserManager();

    public CrateListener(CratePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getItem() == null) return;

        Player player = event.getPlayer();
        CrateUser user = CratePlugin.getPlugin().getUserManager().getUser(player);
        Crate crate = getPlugin().getCrateManager().getCrate(event.getItem());

        if (user == null) return;
        if (crate == null) return;
        event.setCancelled(true);

        if (manager.canOpen(player)) {
            Message.MAX_CRATE.send(player);
            return;
        }

        if (manager.isDelay(player)) {
            Message.WAIT_CRATE.send(player);
            return;
        }

        if (user.isOpening()) {
            Message.ALREADY_OPENING.send(player);
            return;
        }

        crate.open(user);
        user.setOpening(true);

        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        player.spigot().sendMessage(new MessageBuilder(Message.OPEN_CRATE).placeholder("crate_name", crate.getName()).build());

        manager.getUser(player).setDelay(System.currentTimeMillis());
        manager.addCrate(player);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        EditSession session = getPlugin().getCrateManager().getEditorManager().getEditor(player);

        if(!getPlugin().getCrateManager().getEditorManager().isEditing(player)) return;
        if(session == null || session.getItem() == null) return;

        Bukkit.getScheduler().runTask(getPlugin(), () -> {
            if (StringUtils.isNumeric(event.getMessage()) && event.getMessage().split(" ").length == 1) {
                Inventory inventory = Bukkit.createInventory(null, 54, Message.EDIT_CRATE.get());

                for (Reward reward : session.getCrate().getItems()) {
                    inventory.addItem(new ItemBuilder(reward.getItem()).setLore("", "&7Percentage: &f" + reward.getChance() + " %", "", "&eSHIFT CLICK &7Set Percentage",
                            "&eMIDDLE CLICK &7Remove Item").build());
                }

                inventory.addItem(new ItemBuilder(session.getItem()).setLore("",
                        "&7Percentage: &f" + event.getMessage() + " %", "", "&eSHIFT CLICK &7Set Percentage",
                        "&eMIDDLE CLICK &7Remove Item").build());

                player.openInventory(inventory);

                session.setItem(null);
            } else Message.NOT_NUMBER.send(player);
        });
    }


    @EventHandler
    public void onOpen(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(Message.OPENING.get())) {
            event.setCancelled(true);
        }
    }
}
