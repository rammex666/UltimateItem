package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LifeStealEffect implements Listener {

    private double lifeStealPercentage = 20.0; // Pourcentage de vie volée


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

        if (isItemExistWithName(getItemName(itemName))) {
            Items item = ItemManager.getItemByName(getItemName(itemName));
            if (item != null && item.getCustomsEffects() != null) {
                Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                if (effectClass != null && effectClass == LifeStealEffect.class) {
                    // Vérifie si les dégâts sont valides
                    double damage = event.getDamage();
                    if (damage > 0) {
                        // Calcule la vie volée
                        double lifeStealAmount = damage * (lifeStealPercentage / 100);

                        // Ajoute la vie au joueur sans dépasser le maximum
                        double newHealth = Math.min(player.getHealth() + lifeStealAmount, player.getMaxHealth());
                        player.setHealth(newHealth);
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