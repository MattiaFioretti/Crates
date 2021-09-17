package it.matty.crate.messages;

import it.matty.crate.CratePlugin;
import it.matty.crate.config.objects.IConfigFile;
import it.matty.crate.utils.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.bukkit.command.CommandSender;

import java.util.List;


@RequiredArgsConstructor @Getter
public enum Message {
    HELP_MESSAGE("messages.commands.help"),
    RELOAD("messages.commands.reload"),
    LIST_MESSAGE("messages.commands.list.message"),
    LIST_HOVER("messages.commands.list.hover"),
    CREATES_LIST("messages.commands.list.format"),
    OPEN_CRATE("messages.crates.open"),
    MAX_CRATE("messages.crates.error"),
    WAIT_CRATE("messages.crates.wait"),
    NO_PERMISSION("messages.commands.no-permission"),
    CRATE_NOT_EXIST("messages.crates.exist"),
    PLAYER_NOT_ONLINE("messages.commands.player-not-online"),
    SYNTAX_ERROR("messages.commands.syntax-error"),
    CRATE_ALREADY_EXIST("messages.crates.already-exist"),
    CRATE_SAVED("messages.crates.saved"),
    EDIT_CRATE("messages.gui.edit"),
    OPENING("messages.gui.opening"),
    CRATE_DELETE("messages.crates.delete"),
    ALREADY_OPENING("messages.crates.already-opening");

    private final String path;
    private final IConfigFile file = CratePlugin.getPlugin().getFileManager().getFile("messages");

    public void send(CommandSender sender) {
        if (file.getConfig().get(path) instanceof List)
            for (String message : file.getConfig().getStringList(path))
                sender.sendMessage(Utils.textColor(message));
        else sender.sendMessage(Utils.textColor(file.getConfig().getString(path)));
    }

    public String get() {
        return Utils.textColor(file.getConfig().getString(path));
    }
}
