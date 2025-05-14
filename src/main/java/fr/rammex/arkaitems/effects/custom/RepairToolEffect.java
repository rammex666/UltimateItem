package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RepairToolEffect implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = event.getPlayer();
            if(hasRequiredEffect(player)) {
                ItemStack playerHelmet = player.getInventory().getHelmet();
                ItemStack playerChestplate = player.getInventory().getChestplate();
                ItemStack playerLeggings = player.getInventory().getLeggings();
                ItemStack playerBoots = player.getInventory().getBoots();
                if(playerHelmet != null && playerHelmet.getType() != Material.AIR) {
                    playerHelmet.setDurability((short) (playerHelmet.getDurability()+1));
                }
                if(playerChestplate != null && playerChestplate.getType() != Material.AIR) {
                    playerChestplate.setDurability((short) (playerChestplate.getDurability()+1));
                }
                if(playerLeggings != null && playerLeggings.getType() != Material.AIR) {
                    playerLeggings.setDurability((short) (playerLeggings.getDurability()+1));
                }
                if(playerBoots != null && playerBoots.getType() != Material.AIR) {
                    playerBoots.setDurability((short) (playerBoots.getDurability()+1));
                }
                player.sendMessage("§aL'armure a été réparée de 1 point de durabilité.");
                ItemMetadata.updateDurability(player.getInventory().getItemInHand());
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
                if (effectClass != null && effectClass == RepairToolEffect.class) {
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
