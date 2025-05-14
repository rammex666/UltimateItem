package fr.rammex.arkaitems.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Messages {
    public static String getMessage(String key) {
        String message = YamlFiles.getMessagesConf().getString(ChatColor.translateAlternateColorCodes('&', key));
        return message;
    }
}
