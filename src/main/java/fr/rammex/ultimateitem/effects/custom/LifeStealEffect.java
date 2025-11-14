package fr.rammex.ultimateitem.effects.custom;

import fr.rammex.ultimateitem.UltimateItem;
import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.Items;
import fr.rammex.ultimateitem.utils.ItemMetadata;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import static fr.rammex.ultimateitem.utils.Messages.getMessage;

public class LifeStealEffect implements Listener {

    private double lifeStealPercentage = UltimateItem.instance.getConfig().getDouble("custom-effect.life-steal.percentage");; // Pourcentage de vie volée


    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // Vérifie si le joueur est l'attaquant
        Entity damager = event.getDamager();
        if(damager == null || !(damager instanceof Player)) {
            return;
        }
        Player player = (Player) damager;
        ItemStack itemInHand = player.getInventory().getItemInHand();
        if(itemInHand == null || itemInHand.getType() == Material.AIR) {
            return;
        }
        String itemName = itemInHand.getItemMeta().getDisplayName();
        if (itemName == null || itemName.isEmpty()) {
            return;
        }

        if (isItemExistWithName(getItemName(itemName))) {
            Items item = ItemManager.getItemByName(getItemName(itemName));
            if (item != null && item.getCustomsEffects() != null) {
                Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                if (effectClass != null && effectClass == LifeStealEffect.class) {
                    double damage = event.getDamage();
                    if (damage > 0) {
                        double lifeStealAmount = damage * (lifeStealPercentage / 100);

                        double newHealth = Math.min(player.getHealth() + lifeStealAmount, player.getMaxHealth());
                        player.setHealth(newHealth);
                        player.sendMessage(getMessage("custom-effect.LifeStealEffect.proc-message").replace("{amount}", String.valueOf(lifeStealAmount)));
                        ItemMetadata.updateDurability(player.getInventory().getItemInHand());
                    }
                }
            }
        }

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