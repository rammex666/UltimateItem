package fr.rammex.ultimateitem.utils;

import org.bukkit.ChatColor;


public class Messages {
    public static String getMessage(String key) {
        String message = YamlFiles.getMessagesConf().getString(ChatColor.translateAlternateColorCodes('&', key));
        return message;
    }
}
