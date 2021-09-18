package it.matty.crate.commands.subcommands;

import it.matty.crate.CratePlugin;
import it.matty.crate.crates.crates.Crate;
import it.matty.crate.messages.Message;
import it.matty.crate.messages.builder.MessageBuilder;
import org.bukkit.command.CommandSender;

public class ListCommand extends SubCommand {

    public ListCommand(String subcommand) {
        super(subcommand);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("crates.list")) {
            Message.NO_PERMISSION.send(sender);
            return;
        }

        Message.LIST_MESSAGE.send(sender);
        for (Crate crate : CratePlugin.getPlugin().getCrateManager().getCrates()) {
            sender.spigot().sendMessage(new MessageBuilder(Message.CREATES_LIST)
                    .setCommand("/crate give " + sender.getName() + " " + crate.getName())
                    .setHover(Message.LIST_HOVER.get())
                    .placeholder("crate_name", crate.getName()).build());
        }
    }
}
