package fr.rammex.ultimateitem.utils;

import fr.rammex.ultimateitem.UltimateItem;
import org.bukkit.Bukkit;

public class TimesTask {

    public static void waitOneTick(Runnable task) {
        Bukkit.getScheduler().runTaskLater(UltimateItem.instance, task, 1L);
    }
}
