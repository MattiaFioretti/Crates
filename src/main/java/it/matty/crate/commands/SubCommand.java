package it.matty.crate.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@AllArgsConstructor @Getter
public abstract class SubCommand {
    private final String subcommand;
    private final int minargs;

    public abstract void execute(CommandSender sender, String[] args);
}
