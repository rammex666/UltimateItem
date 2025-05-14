package fr.rammex.arkaitems.items.specialitems.pickaxemultiblock;

import fr.rammex.arkaitems.database.ItemMetaDataManager;
import fr.rammex.arkaitems.items.specialitems.autoplaceblock.AutoPlaceBlock;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickaxeMultiBlockManager {
    private static Map<String, PickaxeMultiBlock> items = new HashMap<>();

    public static void addItem(PickaxeMultiBlock item) {
        items.put(item.getId(), item);
    }

    public static PickaxeMultiBlock getItemById(String id) {
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

    public static Map<String, PickaxeMultiBlock> getItems() {
        return items;
    }

    public static void removeItem(String id) {
        items.remove(id);
    }

    public static void removeItem(PickaxeMultiBlock item) {
        items.remove(item.getId());
    }

    public static PickaxeMultiBlock getItemByName(String name) {
        for (PickaxeMultiBlock autoPlaceBlock : items.values()) {
            if (autoPlaceBlock.getName().equalsIgnoreCase(name)) {
                return autoPlaceBlock;
            }
        }
        return null;
    }

    public static ItemStack createItem(Player player, String name, int amount) {
        PickaxeMultiBlock sellStick = getItemById(name);
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

        ItemMetaDataManager.insertNewOwnerItem(player.getUniqueId(), "PickaxeMultiBlock:"+sellStick.getId());
        return itemStack;
    }
}
