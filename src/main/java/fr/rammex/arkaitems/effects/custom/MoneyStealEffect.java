package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.effects.CustomsEffects;
import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class MoneyStealEffect implements Listener {

    private static final int MINIMUM_MONEY = 0;
    private static final int MAXIMUM_MONEY = 100;
    private static final int STEAL_CHANCE = 10; // Pourcentage de chance de déclencher l'effet

    private final Random random = new Random();

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        if (!hasRequiredEffect(attacker)) {
            return;
        }

        if (random.nextInt(100) < STEAL_CHANCE) {
            int stolenAmount = random.nextInt(MAXIMUM_MONEY - MINIMUM_MONEY + 1) + MINIMUM_MONEY;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco take " + victim.getName() + " " + stolenAmount);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + attacker.getName() + " " + stolenAmount);
            attacker.sendMessage(ChatColor.GREEN + "Vous avez volé " + stolenAmount + " à " + victim.getName() + " !");
            victim.sendMessage(ChatColor.RED + "Vous avez perdu " + stolenAmount + " à cause de " + attacker.getName() + " !");
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
                if (effectClass != null && effectClass == MoneyStealEffect.class) {
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