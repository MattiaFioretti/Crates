package it.matty.crate.commands.subcommands;

import org.bukkit.command.CommandSender;

import java.io.IOException;

public interface ISubCommand {

    String getSubcommand();

    void execute(CommandSender sender, String[] args) throws IOException;
}
