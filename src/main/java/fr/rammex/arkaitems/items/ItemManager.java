package fr.rammex.arkaitems.items;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static Map<String, Items> itemsMap = new HashMap<>();

    public static void addItem(Items item) {
        itemsMap.put(item.getId(), item);
    }

    public static Items getItemById(String id) {
        return itemsMap.get(id);
    }

    public static boolean isItemExist(String id) {
        return itemsMap.containsKey(id);
    }

    public static void resetItems() {
        itemsMap.clear();
    }

    public static ItemStack createItem(String name, int amount) {
        Items item = getItemById(name);
        if (item == null) {
            return null;
        }
        ItemStack itemStack = new ItemStack(item.getMaterial(), amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', item.getName()));
        itemMeta.setLore(item.getLore());
        itemStack.setItemMeta(itemMeta);


        return itemStack;
    }
}
