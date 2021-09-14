package it.matty.crate.commands;

import it.matty.crate.CratePlugin;
import it.matty.crate.commands.subcommands.ListCommand;
import it.matty.crate.utils.Utils;
import org.bukkit.command.CommandSender;

public class CrateCommand extends CommandFramework {

    public CrateCommand(CratePlugin plugin, String command) {
        super(plugin, command);
    }

    @Override
    public void registerSubcommands() {
        getSubCommands().add(new ListCommand("list", 1));
    }

    @Override
    public void execute(CommandSender sender) {
        sender.sendMessage(Utils.textColor(""));
    }
}
