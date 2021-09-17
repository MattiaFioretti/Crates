package it.matty.crate.commands.subcommands;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.cratekey.Crate;
import it.matty.crate.crates.cratekey.CrateKey;
import it.matty.crate.crates.rewards.Reward;
import it.matty.crate.messages.Message;
import it.matty.crate.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;

public class EditCommand extends SubCommand {

    public EditCommand(String subcommand) {
        super(subcommand);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;

        if(!sender.hasPermission("crates.edit")) {
            Message.NO_PERMISSION.send(sender);
            return;
        }

        if(args.length != 2) {
            Message.SYNTAX_ERROR.send(sender);
            return;
        }

        Crate crate = CratePlugin.getPlugin().getCrateManager().getCrate(args[1]);
        if(crate == null) {
            Message.CRATE_NOT_EXIST.send(sender);
            return;
        }

        Inventory inventory = Bukkit.createInventory(null, 54, Message.EDIT_CRATE.get());

        for(Reward reward : crate.getItems()) {
            inventory.addItem(new ItemBuilder(reward.getItem()).setLore("",
                    "&7Percentage: &f" + reward.getChance() + " %", "", "&eRIGHT CLICK &7+1 Percentage",
                    "&eLEFT CLICK &7+1 Percentage", "&eMIDDLE CLICK &7Remove Item").build());
        }

        player.openInventory(inventory);

        CratePlugin.getPlugin().getCrateManager().getEditorManager().addEditor(crate, player);
    }
}