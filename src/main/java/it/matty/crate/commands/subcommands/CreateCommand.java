package it.matty.crate.commands.subcommands;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.crates.crates.DefaultCrate;
import it.matty.crate.messages.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;

public class CreateCommand extends SubCommand {

    public CreateCommand(String subcommand) {
        super(subcommand);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;

        if (!sender.hasPermission("crates.create")) {
            Message.NO_PERMISSION.send(sender);
            return;
        }

        if (args.length != 2) {
            Message.SYNTAX_ERROR.send(sender);
            return;
        }

        Crate crate = CratePlugin.getPlugin().getCrateManager().getCrate(args[1]);
        if (crate != null) {
            Message.CRATE_ALREADY_EXIST.send(sender);
            return;
        }

        Crate newCrate = new DefaultCrate(args[1], player.getInventory().getItemInMainHand(), new HashSet<>());

        Inventory inventory = Bukkit.createInventory(null, 54, Message.EDIT_CRATE.get());
        player.openInventory(inventory);


        CratePlugin.getPlugin().getCrateManager().addCrate(newCrate);
        CratePlugin.getPlugin().getCrateManager().getEditorManager().addEditor(newCrate, player);
    }
}
