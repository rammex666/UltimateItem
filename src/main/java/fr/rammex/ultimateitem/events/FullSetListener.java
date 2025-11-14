package fr.rammex.ultimateitem.events;

import fr.rammex.ultimateitem.UltimateItem;
import fr.rammex.ultimateitem.fullsets.FullSet;
import fr.rammex.ultimateitem.fullsets.FullSetManager;
import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class FullSetListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            ItemStack currentItem = event.getCurrentItem();

            Player player = (Player) event.getWhoClicked();

            // armure +
            Bukkit.getScheduler().runTaskLater(UltimateItem.instance, () -> {
                for (ItemStack armorPiece : player.getInventory().getArmorContents()) {
                    if (armorPiece != null && armorPiece.hasItemMeta()) {
                        String itemName = armorPiece.getItemMeta().getDisplayName();
                        if (isItemExistWithName(getItemName(itemName))) {
                            Items item = ItemManager.getItemByName(getItemName(itemName));
                            if (FullSetManager.isFullSet(item)) {
                                FullSet fullSet = FullSetManager.getFullSet(item);
                                if (isWearingFullSet(player, fullSet)) {
                                    List<String> effectsOnFullSet = fullSet.getFullSetEffects();
                                    applyPotionEffectsFromConfig(player, effectsOnFullSet);
                                } else {
                                    List<String> effectsOnFullSet = fullSet.getFullSetEffects();
                                    removePotionEffectsFromConfig(player, effectsOnFullSet);
                                }
                            }
                        }
                    }
                }
            }, 20L); // 40 ticks = 2 secondes


            // armure -
            Bukkit.getScheduler().runTaskLater(UltimateItem.instance, () -> {
                if (currentItem != null && currentItem.hasItemMeta()) {
                    String itemName = currentItem.getItemMeta().getDisplayName();
                    if(isItemExistWithName(getItemName(itemName))) {
                        Items item = ItemManager.getItemByName(getItemName(itemName));
                        if(FullSetManager.isFullSet(item)){
                            FullSet fullSet = FullSetManager.getFullSet(item);
                            if(!isWearingFullSet((Player) event.getWhoClicked(), fullSet)){
                                List<String> effectsOnFullSet = fullSet.getFullSetEffects();
                                removePotionEffectsFromConfig((Player) event.getWhoClicked(), effectsOnFullSet);
                            }
                        }
                    }
                }
            }, 20L);
        }
    }

    private boolean isItemExistWithName(String name) {
        if (ItemManager.getItemByName(name) != null) {
            return true;}
        else {
            return false;
        }
    }

    public boolean isWearingFullSet(Player player, FullSet fullSet) {
        List<String> requiredItems = fullSet.getSetItems();
        List<String> foundItems = new ArrayList<>();

        for (ItemStack armorPiece : player.getInventory().getArmorContents()) {
            if (armorPiece != null && armorPiece.hasItemMeta()) {
                String itemName = armorPiece.getItemMeta().getDisplayName();
                Items item = ItemManager.getItemByName(getItemName(itemName));
                if (requiredItems.contains(item.getId())) {
                    foundItems.add(item.getId());
                }
            }
        }

        return foundItems.containsAll(requiredItems);
    }

    private String getItemName(String name) {
        return name.replace("§", "&");
    }

    public static void applyPotionEffectsFromConfig(Player player, List<String> effects) {
        for (String effectEntry : effects) {

            String[] parts = effectEntry.split(":");
            if (parts.length == 2) {
                String effectName = parts[0];
                int level;
                try {
                    level = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid potion effect level: " + parts[1]);
                    continue;
                }
                PotionEffectType effectType = PotionEffectType.getByName(effectName.toUpperCase());
                if (effectType != null) {
                    player.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, level - 1));
                } else {
                    System.out.println("Potion effect not found: " + effectName);
                }
            }
        }
    }

    public static void removePotionEffectsFromConfig(Player player, List<String> effects) {
        for (String effectEntry : effects) {
            String[] parts = effectEntry.split(":");
            if (parts.length == 2) {
                String effectName = parts[0];
                PotionEffectType effectType = PotionEffectType.getByName(effectName.toUpperCase());
                if (effectType != null) {
                    player.removePotionEffect(effectType);
                } else {
                    System.out.println("Potion effect not found: " + effectName);
                }
            }
        }
    }
}
