package fr.rammex.arkaitems.effects.custom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class StealthEffect {

    private static final String STEALTH_TEAM_NAME = "stealth";

    public static void enableStealth(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(STEALTH_TEAM_NAME);

        if (team == null) {
            team = scoreboard.registerNewTeam(STEALTH_TEAM_NAME);
            team.setNameTagVisibility(NameTagVisibility.NEVER);
        }

        team.addEntry(player.getName());
        player.setScoreboard(scoreboard);

        player.sendMessage(ChatColor.GRAY + "Vous êtes maintenant en mode furtif.");
    }

    public static void disableStealth(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(STEALTH_TEAM_NAME);

        if (team != null && team.hasEntry(player.getName())) {
            team.removeEntry(player.getName());
        }

        player.setScoreboard(scoreboard);
        player.sendMessage(ChatColor.GRAY + "Vous n'êtes plus en mode furtif.");
    }
}