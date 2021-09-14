package it.matty.crate.commands;

import it.matty.crate.commands.annotations.CommandInfo;
import it.matty.crate.commands.exception.CommandException;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class CommandFramework implements CommandExecutor {
    @Getter private final Set<SubCommand> subCommands = new HashSet<>();
    
    public CommandFramework(JavaPlugin plugin) {
        if (!getClass().isAnnotationPresent(CommandInfo.class)) return;
        plugin.getCommand(getClass().getAnnotation(CommandInfo.class).value()).setExecutor(this);
    }
    
    public void registerSubcommand(SubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        try {
            if (args.length == 0) {
                execute(sender);
            } else {
                for (SubCommand subCommand : subCommands) {
                    if (!args[0].equalsIgnoreCase(subCommand.getSubcommand())) continue;
                    if (args.length != subCommand.getMinargs()) continue;
                    
                    subCommand.execute(sender, args);
                    break;
                }
            }
            return true;
        } catch (CommandException exception) {
            sender.sendMessage(exception.getMessage());
        }
        return true;
    }
    
    public abstract void execute(CommandSender sender);
}
