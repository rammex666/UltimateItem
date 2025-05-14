package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class TeleportEffect implements Listener {

    private final int teleportRadius = 50;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta()) {
                String itemName = item.getItemMeta().getDisplayName();

                if (isItemExistWithName(getItemName(itemName))) {
                    Items customItem = ItemManager.getItemByName(getItemName(itemName));
                    if (customItem != null && customItem.getCustomsEffects() != null) {
                        Class<?> effectClass = customItem.getCustomsEffects().getEffectClass();
                        if (effectClass != null && effectClass == TeleportEffect.class) {
                            Location deathLocation = player.getLocation();
                            Location teleportLocation = getRandomLocation(deathLocation, teleportRadius);

                            player.teleport(teleportLocation);
                            ItemMetadata.updateDurability(player.getInventory().getItemInHand());
                            break;
                        }
                    }
                }
            }
        }
    }

    private Location getRandomLocation(Location center, int radius) {
        Random random = new Random();
        double xOffset = (random.nextDouble() * 2 - 1) * radius;
        double zOffset = (random.nextDouble() * 2 - 1) * radius;
        double newX = center.getX() + xOffset;
        double newZ = center.getZ() + zOffset;
        double newY = center.getWorld().getHighestBlockYAt((int) newX, (int) newZ) + 1; // Position au-dessus du sol
        return new Location(center.getWorld(), newX, newY, newZ);
    }

    private String getItemName(String name) {
        return name.replace("§", "&");
    }

    private boolean isItemExistWithName(String name) {
        return ItemManager.getItemByName(name) != null;
    }
}