package it.matty.crate.commands.subcommands;

import org.bukkit.command.CommandSender;

public interface ISubCommand {

    String getSubcommand();

    void execute(CommandSender sender, String[] args);
}
