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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.rammex.arkaitems.utils.Messages.getMessage;

public class RandomItemInventoryEffect implements Listener {

    private final double shuffleChance = ArkaItems.instance.getConfig().getDouble("custom-effect.random-item-iventory.proc");

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            System.out.println(ChatColor.GREEN + "RandomItemInventoryEffect triggered for " + event.getDamager().getName() + " on " + event.getEntity().getName());
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            if(hasRequiredEffect(damager)) {
                System.out.println(ChatColor.GREEN + "RandomItemInventoryEffect triggered for " + damager.getName() + " on " + victim.getName());
                if (Math.random() < shuffleChance) {
                    shuffleInventory(victim);
                    damager.sendMessage(getMessage("custom-effect.RandomItemInventoryEffect.proc-message").replace("{player}", victim.getName()));
                    victim.sendMessage(getMessage("custom-effect.RandomItemInventoryEffect.random-item-message").replace("{player}", damager.getName()));
                }
            }

        }
    }

    private void shuffleInventory(Player player) {
        Inventory inventory = player.getInventory();
        ItemStack[] contents = inventory.getContents();

        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : contents) {
            items.add(item);
        }

        Collections.shuffle(items);
        inventory.setContents(items.toArray(new ItemStack[0]));
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
                if (effectClass != null && effectClass == RandomItemInventoryEffect.class) {
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
