package fr.rammex.arkaitems.items;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.rammex.arkaitems.database.ItemMetaDataManager;
import fr.rammex.arkaitems.utils.ItemMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    private static Map<String, Items> itemsMap = new HashMap<>();

    public static void addItem(Items item) {
        itemsMap.put(item.getId(), item);
    }

    public static Map<String, Items> getItems() {
        return itemsMap;
    }

    public static Items getItemById(String id) {
        return itemsMap.get(id);
    }

    public static Items getItemByName(String name) {
        for (Items item : itemsMap.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static boolean isItemExist(String id) {
        return itemsMap.containsKey(id);
    }

    public static void resetItems() {
        itemsMap.clear();
    }

    public static int getItemCount() {
        return itemsMap.size();
    }

    public static ItemStack createItem(Player player, String name, int amount) {
        Items item = getItemById(name);
        if (item == null) {
            player.sendMessage(ChatColor.RED + "Item with name " + name + " does not exist.");
            return null;
        }
        ItemStack itemStack = new ItemStack(item.getMaterial(), amount);
        String id = ItemMetaDataManager.getNextItemID();
        List<String> enchants = item.getEnchantsAssociated();
        itemStack = ItemMetadata.setMetadata(itemStack, "ID", id);
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (String effectEntry : enchants) {

            String[] parts = effectEntry.split(":");
            if (parts.length == 2) {
                String effectName = parts[0];
                int level;
                try {
                    level = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid enchant effect level: " + parts[1]);
                    continue;
                }
                Enchantment effectType = Enchantment.getByName(effectName.toUpperCase());
                if (effectType != null) {
                    itemMeta.addEnchant(effectType, level, true);
                } else {
                    System.out.println("Enchant not found: " + effectName);
                }
            }
        }
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', item.getName()));
        List<String> lore = item.getLore();
        if (lore != null) {
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, lore.get(i).replace("{owner_name}", player.getName())
                        .replace("{item_id}", id));
            }
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        ItemMetaDataManager.insertNewOwnerItem(player.getUniqueId(), "Item:"+item.getId());

        return itemStack;
    }

}
