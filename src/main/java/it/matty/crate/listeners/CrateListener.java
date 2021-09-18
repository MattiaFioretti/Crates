package it.matty.crate.listeners;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.listeners.manager.ListenerManager;
import it.matty.crate.messages.Message;
import it.matty.crate.messages.builder.MessageBuilder;
import it.matty.crate.users.IUserManager;
import it.matty.crate.users.objects.CrateUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
    public void onOpen(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(Message.OPENING.get())) {
            event.setCancelled(true);
        }
    }
}
