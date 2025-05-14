package fr.rammex.arkaitems.items.specialitems.sellstick;

import fr.rammex.arkaitems.database.ItemMetaDataManager;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellStickManager {
    private static Map<String, SellStick> sellSticks = new HashMap<>();

    public static void addSellStick(SellStick sellStick) {
        sellSticks.put(sellStick.getId(), sellStick);
    }

    public static SellStick getSellStickById(String id) {
        return sellSticks.get(id);
    }

    public static boolean isSellStickExist(String id) {
        return sellSticks.containsKey(id);
    }

    public static void resetSellSticks() {
        sellSticks.clear();
    }

    public static int getSellStickCount() {
        return sellSticks.size();
    }

    public static Map<String, SellStick> getSellSticks() {
        return sellSticks;
    }

    public static void removeSellStick(String id) {
        sellSticks.remove(id);
    }

    public static void removeSellStick(SellStick sellStick) {
        sellSticks.remove(sellStick.getId());
    }

    public static SellStick getSellStickByName(String name) {
        for (SellStick sellStick : sellSticks.values()) {
            if (sellStick.getName().equalsIgnoreCase(name)) {
                return sellStick;
            }
        }
        return null;
    }

    public static ItemStack createSellStick(Player player, String name, int amount) {
        SellStick sellStick = getSellStickById(name);
        if (sellStick == null) {
            return null;
        }
        ItemStack itemStack = new ItemStack(sellStick.getMaterial(), amount);
        String id = ItemMetaDataManager.getNextItemID();
        itemStack = ItemMetadata.setMetadata(itemStack, "ID", id);
        itemStack = ItemMetadata.setMetadata(itemStack, "Multiplier", String.valueOf(sellStick.getSellMultiplier()));
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

        ItemMetaDataManager.insertNewOwnerItem(player.getUniqueId(), "SellStick:"+sellStick.getId());
        return itemStack;
    }
}
