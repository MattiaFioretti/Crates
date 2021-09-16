package it.matty.crate.commands;

import it.matty.crate.CratePlugin;
import it.matty.crate.commands.subcommands.ISubCommand;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

public abstract class CommandFramework implements CommandExecutor, ICommandFramework {
    @Getter
    private final Set<ISubCommand> subCommands = new HashSet<>();

    public CommandFramework(CratePlugin plugin, String command) {
        plugin.getCommand(command).setExecutor(this);
        registerSubcommands();
    }

    @Override @SneakyThrows
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length == 0)
            execute(sender);
        else {
            for (ISubCommand subCommand : subCommands) {
                if (!args[0].equalsIgnoreCase(subCommand.getSubcommand())) continue;
                subCommand.execute(sender, args);
                break;
            }
        }
        return true;
    }
}
