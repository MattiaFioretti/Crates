package it.matty.crate.commands;

import it.matty.crate.CratePlugin;
import it.matty.crate.commands.subcommands.GiveCommand;
import it.matty.crate.commands.subcommands.ListCommand;
import it.matty.crate.commands.subcommands.ReloadCommand;
import it.matty.crate.messages.Message;
import org.bukkit.command.CommandSender;

public class CrateCommand extends CommandFramework {

    public CrateCommand(CratePlugin plugin, String command) {
        super(plugin, command);
    }

    @Override
    public void registerSubcommands() {
        getSubCommands().add(new ListCommand("list"));
        getSubCommands().add(new ReloadCommand("reload"));
        getSubCommands().add(new GiveCommand("give"));
    }

    @Override
    public void execute(CommandSender sender) {
        Message.HELP_MESSAGE.send(sender);
    }
}
