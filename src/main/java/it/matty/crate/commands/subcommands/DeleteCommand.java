package it.matty.crate.commands.subcommands;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.messages.Message;
import org.bukkit.command.CommandSender;

public class DeleteCommand extends SubCommand {
    public DeleteCommand(String subcommand) {
        super(subcommand);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("crates.delete")) {
            Message.NO_PERMISSION.send(sender);
            return;
        }

        if (args.length < 2) {
            Message.HELP_MESSAGE.send(sender);
            return;
        }

        Crate crate = CratePlugin.getPlugin().getCrateManager().getCrate(args[1]);

        if (crate == null) {
            Message.CRATE_NOT_EXIST.send(sender);
            return;
        }

        CratePlugin.getPlugin().getCrateManager().removeCrate(crate);
        Message.CRATE_DELETE.send(sender);
    }
}
