package it.matty.crate.commands;

import org.bukkit.command.CommandSender;

public interface ICommandFramework {

    void registerSubcommands();

    void execute(CommandSender sender);
}
