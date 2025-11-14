package fr.rammex.ultimateitem.effects.custom;

import fr.rammex.ultimateitem.UltimateItem;
import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.Items;
import fr.rammex.ultimateitem.utils.ItemMetadata;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static fr.rammex.ultimateitem.utils.Messages.getMessage;

public class MoneyStealEffect implements Listener {

    private static final int MINIMUM_MONEY = UltimateItem.instance.getConfig().getInt("custom-effect.money-steal.min");
    private static final int MAXIMUM_MONEY = UltimateItem.instance.getConfig().getInt("custom-effect.money-steal.max");;
    private static final double STEAL_CHANCE = UltimateItem.instance.getConfig().getDouble("custom-effect.money-steal.steal-chance"); // Pourcentage de chance de déclencher l'effet

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

        if (random.nextDouble() * 100 < STEAL_CHANCE) {
            int stolenAmount = random.nextInt(MAXIMUM_MONEY - MINIMUM_MONEY + 1) + MINIMUM_MONEY;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco take " + victim.getName() + " " + stolenAmount);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + attacker.getName() + " " + stolenAmount);
            attacker.sendMessage(getMessage("custom-effect.MoneyStealEffect.proc-message").replace("{money}", String.valueOf(stolenAmount)));
            victim.sendMessage(getMessage("custom-effect.MoneyStealEffect.steal-message").replace("{money}", String.valueOf(stolenAmount)));
            ItemMetadata.updateDurability(attacker.getInventory().getItemInHand());
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