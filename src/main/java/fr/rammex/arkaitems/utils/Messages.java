package fr.rammex.arkaitems.utils;

import org.bukkit.entity.Player;


public class Messages {
    public static String getMessage(String key, Player player) {
        String message = YamlFiles.getMessagesConf().getString(key);
        return message;
    }
}
