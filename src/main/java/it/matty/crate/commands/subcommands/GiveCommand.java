package it.matty.crate.commands.subcommands;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.messages.Message;
import it.matty.crate.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends SubCommand {
    public GiveCommand(String subcommand) {
        super(subcommand);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("crates.give")) {
            Message.NO_PERMISSION.send(sender);
            return;
        }

        if(args.length < 3) {
            Message.HELP_MESSAGE.send(sender);
            return;
        }

        Crate crate = CratePlugin.getPlugin().getCrateManager().getCrate(args[2]);
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

        if(crate == null) {
            Message.CRATE_NOT_EXIST.send(sender);
            return;
        }

        if(!player.isOnline()) {
            Message.PLAYER_NOT_ONLINE.send(sender);
            return;
        }

        if(args.length == 3) {
            ((Player) player).getInventory().addItem(crate.getCrate());

        } else if (args.length == 4) {
            if(!Utils.isNumber(args[3])) {
                Message.SYNTAX_ERROR.send(sender);
                return;
            }

            ItemStack crates = crate.getCrate();
            crates.setAmount(Integer.parseInt(args[3]));

            ((Player) player).getInventory().addItem(crates);
        }
    }
}
