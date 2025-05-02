package fr.rammex.arkaitems.utils;

import fr.rammex.arkaitems.ArkaItems;
import org.bukkit.Bukkit;

public class TimesTask {

    public static void waitOneTick(Runnable task) {
        Bukkit.getScheduler().runTaskLater(ArkaItems.instance, task, 1L);
    }
}
