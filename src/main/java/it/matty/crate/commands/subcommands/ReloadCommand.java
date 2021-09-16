package it.matty.crate.commands.subcommands;

import it.matty.crate.CratePlugin;
import it.matty.crate.messages.Message;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(String subcommand) {
        super(subcommand);
    }

    @Override @SneakyThrows
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("crates.reload")) {
            Message.NO_PERMISSION.send(sender);
            return;
        }
        CratePlugin.getPlugin().getFileManager().getFile("messages").reload();
        Message.RELOAD.send(sender);
    }
}
