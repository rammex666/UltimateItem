package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class NoFallEffect implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                ItemStack boots = player.getInventory().getBoots();

                if (boots != null && boots.hasItemMeta()) {
                    String itemName = boots.getItemMeta().getDisplayName();

                    if (ItemManager.getItemByName(itemName) != null) {
                        Items item = ItemManager.getItemByName(itemName);
                        if (item != null && item.getCustomsEffects() != null) {
                            Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                            if (effectClass != null && effectClass == NoFallEffect.class) {
                                event.setCancelled(true); // Annule les dégâts de chute
                            }
                        }
                    }
                }
            }
        }
    }
}