package fr.rammex.ultimateitem.items.specialitems.autoplaceblock;

import fr.rammex.ultimateitem.database.ItemMetaDataManager;
import fr.rammex.ultimateitem.utils.ItemMetadata;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoPlaceBlockManager {
    private static Map<String, AutoPlaceBlock> items = new HashMap<>();

    public static void addItem(AutoPlaceBlock item) {
        items.put(item.getId(), item);
    }

    public static AutoPlaceBlock getItemById(String id) {
        return items.get(id);
    }

    public static boolean isItemExist(String id) {
        return items.containsKey(id);
    }

    public static void resetItem() {
        items.clear();
    }

    public static int getItemCount() {
        return items.size();
    }

    public static Map<String, AutoPlaceBlock> getItems() {
        return items;
    }

    public static void removeItem(String id) {
        items.remove(id);
    }

    public static void removeItem(AutoPlaceBlock item) {
        items.remove(item.getId());
    }

    public static AutoPlaceBlock getItemByName(String name) {
        for (AutoPlaceBlock autoPlaceBlock : items.values()) {
            if (autoPlaceBlock.getName().equalsIgnoreCase(name)) {
                return autoPlaceBlock;
            }
        }
        return null;
    }

    public static ItemStack createItem(Player player, String name, int amount) {
        AutoPlaceBlock sellStick = getItemById(name);
        if (sellStick == null) {
            return null;
        }
        ItemStack itemStack = new ItemStack(sellStick.getMaterial(), amount);
        String id = ItemMetaDataManager.getNextItemID();
        itemStack = ItemMetadata.setMetadata(itemStack, "ID", id);
        itemStack = ItemMetadata.setMetadata(itemStack, "Durability", String.valueOf(sellStick.getDurability()));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', sellStick.getName()));
        List<String> lore = sellStick.getLore();
        if (lore != null) {
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, lore.get(i).replace("{owner_name}", player.getName())
                        .replace("{item_id}", id));
            }
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        ItemMetaDataManager.insertNewOwnerItem(player.getUniqueId(), "AutoBlockPlacer:"+sellStick.getId());
        return itemStack;
    }
}
