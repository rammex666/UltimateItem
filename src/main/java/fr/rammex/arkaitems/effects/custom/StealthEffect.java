package fr.rammex.arkaitems.effects.custom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static fr.rammex.arkaitems.utils.Messages.getMessage;

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

        player.sendMessage(getMessage("custom-effect.StealthEffect.proc-message"));
    }

    public static void disableStealth(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(STEALTH_TEAM_NAME);

        if (team != null && team.hasEntry(player.getName())) {
            team.removeEntry(player.getName());
        }

        player.setScoreboard(scoreboard);
        player.sendMessage(getMessage("custom-effect.StealthEffect.end-message"));
    }
}