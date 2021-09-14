package it.matty.crate.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class Utils {

    public String textColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
