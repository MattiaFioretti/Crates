package it.matty.crate.commands.subcommands;

import org.bukkit.command.CommandSender;

public interface ISubCommand {

    void execute(CommandSender sender, String[] args);
}
