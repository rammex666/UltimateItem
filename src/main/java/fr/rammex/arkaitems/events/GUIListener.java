package fr.rammex.arkaitems.events;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("§8[§6ArkaItems§8]")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

                if(isItemExistWithName(getItemName(itemName))) {
                    Items item = ItemManager.getItemByName(getItemName(itemName));
                    if (item != null) {
                        ItemStack itemToGive = ItemManager.createItem((Player) event.getWhoClicked(), item.getId(), 1);
                        if (itemToGive != null) {
                            event.getWhoClicked().getInventory().addItem(itemToGive);
                        } else {
                            event.getWhoClicked().sendMessage("Failed to create item.");
                        }
                    } else {
                        event.getWhoClicked().sendMessage("Item with name " + itemName + " does not exist.");
                    }
                } else {
                    event.getWhoClicked().sendMessage("Item with name " + itemName + " does not exist.");
                }
            }
        }
    }

    private boolean isItemExistWithName(String name) {
        if (ItemManager.getItemByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String getItemName(String name) {
        return name.replace("§", "&");
    }

}
