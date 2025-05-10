package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TpToPlayerEffect implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = event.getPlayer();
            if(hasRequiredEffect(player)){
                event.setCancelled(true);
                Player nearestPlayer = getNearestPlayer(player);
                if(nearestPlayer != null){
                    player.teleport(nearestPlayer.getLocation());
                    player.sendMessage("§aVous avez été téléporté vers " + nearestPlayer.getName());
                } else {
                    player.sendMessage("§cAucun joueur à proximité.");
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
                if (effectClass != null && effectClass == TpToPlayerEffect.class) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private Player getNearestPlayer(Player player) {
        Player nearestPlayer = null;
        double nearestDistance = 4.0;

        for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
            if (onlinePlayer != player) {
                double distance = player.getLocation().distance(onlinePlayer.getLocation());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPlayer = onlinePlayer;
                }
            }
        }
        return nearestPlayer;
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
