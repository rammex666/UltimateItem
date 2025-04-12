package fr.rammex.arkaitems.effects.custom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.weather.LightningStrikeEvent;

public class BoltEffect implements Listener {

    private static Player summoner;

    public static void applyBoltEffect(Player player) {
        summoner = player;
        Location playerLocation = player.getLocation();

        LightningStrike lightning = player.getWorld().strikeLightning(playerLocation);
        lightning.setFireTicks(0);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LightningStrike && event.getEntity() instanceof Player) {
            Player damagedPlayer = (Player) event.getEntity();
            if (damagedPlayer.equals(summoner)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLightningStrike(LightningStrikeEvent event) {
        LightningStrike lightning = event.getLightning();
        Location location = lightning.getLocation();

        if (location.getBlock().getType() == Material.FIRE) {
            location.getBlock().setType(Material.AIR);
        }

        // Parcourir les blocs autour de l'éclair dans un rayon de 3 blocs
        int radius = 3;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLocation = location.clone().add(x, y, z);
                    if (blockLocation.getBlock().getType() == Material.FIRE) {
                        blockLocation.getBlock().setType(Material.AIR); // Supprimer le feu
                    }
                }
            }
        }
    }
}