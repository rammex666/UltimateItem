package fr.rammex.arkaitems.events;

import fr.rammex.arkaitems.ArkaItems;
import fr.rammex.arkaitems.effects.CustomsEffects;
import fr.rammex.arkaitems.effects.custom.StealthEffect;
import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class CustomEffectsListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            ItemStack currentItem = event.getCurrentItem();
            ItemStack cursorItem = event.getCursor();

            // armure +
            if (cursorItem != null && cursorItem.hasItemMeta()) {
                String itemName = cursorItem.getItemMeta().getDisplayName();
                if(isItemExistWithName(getItemName(itemName))) {
                    Items item = ItemManager.getItemByName(getItemName(itemName));
                    CustomsEffects customsEffects = item.getCustomsEffects();
                    if (customsEffects != null) {
                        Class<?> effectClass = customsEffects.getEffectClass();
                        if (effectClass != null) {
                            if(effectClass == StealthEffect.class){
                                StealthEffect.enableStealth((Player) event.getWhoClicked());
                            }
                        }
                    }
                }
            }

            // armure -
            if (currentItem != null && currentItem.hasItemMeta()) {
                String itemName = currentItem.getItemMeta().getDisplayName();
                if(isItemExistWithName(getItemName(itemName))) {
                    Items item = ItemManager.getItemByName(getItemName(itemName));
                    CustomsEffects customsEffects = item.getCustomsEffects();
                    if (customsEffects != null) {
                        Class<?> effectClass = customsEffects.getEffectClass();
                        if (effectClass != null) {
                            if(effectClass == StealthEffect.class){
                                StealthEffect.disableStealth((Player) event.getWhoClicked());
                            }
                        }
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