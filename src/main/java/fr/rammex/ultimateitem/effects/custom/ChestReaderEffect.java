package fr.rammex.ultimateitem.effects.custom;

import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.Items;
import fr.rammex.ultimateitem.utils.ItemMetadata;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestReaderEffect implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
            Chest chest = (Chest) event.getClickedBlock().getState();
            if (hasRequiredEffect(player)) {
                event.setCancelled(true);
                ItemMetadata.updateDurability(player.getInventory().getItemInHand());
                readChest(chest, player);
            }
        }
    }

    private boolean hasRequiredEffect(Player player) {
        ItemStack itemInHand = player.getInventory().getItemInHand();
        if(itemInHand == null || itemInHand.getType() == Material.AIR) {
            return false;
        }
        String itemName = itemInHand.getItemMeta().getDisplayName();

        if(itemName == null || itemName.isEmpty()) {
            return false;
        }

        if (isItemExistWithName(getItemName(itemName))) {
            Items item = ItemManager.getItemByName(getItemName(itemName));
            if (item != null && item.getCustomsEffects() != null) {
                Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                if (effectClass != null && effectClass == ChestReaderEffect.class) {
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


    private void readChest(Chest chest, Player player) {
        int chestSize = chest.getInventory().getSize();
        Inventory chestReader = Bukkit.createInventory(null, chestSize, "Chest Reader");
        chestReader.setContents(chest.getInventory().getContents());
        player.openInventory(chestReader);
    }
}
