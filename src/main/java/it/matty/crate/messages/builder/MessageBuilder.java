package it.matty.crate.messages.builder;

import it.matty.crate.messages.Message;
import it.matty.crate.utils.Utils;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

@Getter
public class MessageBuilder {
    private final TextComponent component;

    public MessageBuilder(Message message) {
        this.component = new TextComponent(Utils.textColor(message.getFile().getConfig().getString(message.getPath())));
    }

    public MessageBuilder placeholder(String before, String after) {
        this.component.setText(component.getText().replace('%' + before + '%', after));
        return this;
    }

    public MessageBuilder setHover(String text) {
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(text)));
        return this;
    }

    public MessageBuilder setUrl(String url) {
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }

    public MessageBuilder setCommand(String command) {
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    public MessageBuilder setSuggestCommand(String command) {
        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        return this;
    }

    public TextComponent build() {
        return component;
    }
}
