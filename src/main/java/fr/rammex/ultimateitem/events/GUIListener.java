package fr.rammex.ultimateitem.events;

import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.Items;
import fr.rammex.ultimateitem.items.specialitems.autoplaceblock.AutoPlaceBlock;
import fr.rammex.ultimateitem.items.specialitems.autoplaceblock.AutoPlaceBlockManager;
import fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock.PickaxeMultiBlock;
import fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock.PickaxeMultiBlockManager;
import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStick;
import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStickManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("§8[§6UltimateItem§8]")) {
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
                } else if (isSellStickExistWithName(getItemName(itemName))) {
                    SellStick sellStick = SellStickManager.getSellStickByName(getItemName(itemName));
                    if (sellStick != null) {
                        ItemStack itemToGive = SellStickManager.createSellStick((Player) event.getWhoClicked(), sellStick.getId(), 1);
                        if (itemToGive != null) {
                            event.getWhoClicked().getInventory().addItem(itemToGive);
                        } else {
                            event.getWhoClicked().sendMessage("Failed to create sell stick.");
                        }
                    } else {
                        event.getWhoClicked().sendMessage("Sell stick with name " + itemName + " does not exist.");
                    }
                } else if (isAutoPlaceBlockWithName(getItemName(itemName))) {
                    AutoPlaceBlock autoPlaceBlock = AutoPlaceBlockManager.getItemByName(getItemName(itemName));
                    if (autoPlaceBlock != null) {
                        ItemStack itemToGive = AutoPlaceBlockManager.createItem((Player) event.getWhoClicked(), autoPlaceBlock.getId(), 1);
                        if (itemToGive != null) {
                            event.getWhoClicked().getInventory().addItem(itemToGive);
                        } else {
                            event.getWhoClicked().sendMessage("Failed to create autoPlaceBlock.");
                        }
                    } else {
                        event.getWhoClicked().sendMessage("autoPlaceBlock with name " + itemName + " does not exist.");
                    }
                }else if (isPickaxeExistWithName(getItemName(itemName))) {
                    PickaxeMultiBlock pickaxeMultiBlock = PickaxeMultiBlockManager.getItemByName(getItemName(itemName));
                    if (pickaxeMultiBlock != null) {
                        ItemStack itemToGive = PickaxeMultiBlockManager.createItem((Player) event.getWhoClicked(), pickaxeMultiBlock.getId(), 1);
                        if (itemToGive != null) {
                            event.getWhoClicked().getInventory().addItem(itemToGive);
                        } else {
                            event.getWhoClicked().sendMessage("Failed to create pickaxeMultiBlock.");
                        }
                    } else {
                        event.getWhoClicked().sendMessage("pickaxeMultiBlock with name " + itemName + " does not exist.");
                    }
                }  else {
                    event.getWhoClicked().sendMessage("Item with name " + itemName + " does not exist.");
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
    private boolean isSellStickExistWithName(String name) {
        if (SellStickManager.getSellStickByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isAutoPlaceBlockWithName(String name) {
        if (AutoPlaceBlockManager.getItemByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isPickaxeExistWithName(String name) {
        if (PickaxeMultiBlockManager.getItemByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }
}
