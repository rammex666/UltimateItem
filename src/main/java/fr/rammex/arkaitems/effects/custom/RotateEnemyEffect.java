package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.ArkaItems;
import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class RotateEnemyEffect implements Listener {
    private double rotateChance = ArkaItems.instance.getConfig().getDouble("custom-effect.rotate-enemy.proc");; // 10% chance de rotate

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            if (hasRequiredEffect(player)) {
                if (Math.random() < rotateChance) {
                    event.setCancelled(true);
                    victim.sendMessage(ChatColor.GREEN + "You have been rotated!");
                    victim.setVelocity(player.getLocation().getDirection().multiply(-1)); // Rotate the victim
                }
            }
        }
    }

    private boolean hasRequiredEffect(Player player) {
        ItemStack itemInHand = player.getInventory().getItemInHand();
        if(itemInHand == null || itemInHand.getType() == Material.AIR) {
            return false;
        }
        String itemName = itemInHand.getItemMeta().getDisplayName();

        if (isItemExistWithName(getItemName(itemName))) {
            Items item = ItemManager.getItemByName(getItemName(itemName));
            if (item != null && item.getCustomsEffects() != null) {
                Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                if (effectClass != null && effectClass == RotateEnemyEffect.class) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private String getItemName(String name) {
        return name.replace("§", "&");
    }
    private boolean isItemExistWithName(String name) {
        if (ItemManager.getItemByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }
}
