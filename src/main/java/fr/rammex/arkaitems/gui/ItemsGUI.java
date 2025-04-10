package fr.rammex.arkaitems.gui;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.items.specialitems.sellstick.SellStick;
import fr.rammex.arkaitems.items.specialitems.sellstick.SellStickManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemsGUI {

    public static void buildInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, "§8[§6ArkaItems§8]");

        List<ItemStack> items = new ArrayList<>();

        Map<String, Items> itemsMap = ItemManager.getItems();

        for (Map.Entry<String, Items> entry : itemsMap.entrySet()) {
            Items item = entry.getValue();
            ItemStack itemStack = new ItemStack(item.getMaterial(), 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', item.getName()));
            itemMeta.setLore(item.getLore());
            itemStack.setItemMeta(itemMeta);

            items.add(itemStack);
        }

        for (int i = 0; i < items.size(); i++) {
            inventory.setItem(i, items.get(i));
        }

        List<ItemStack> sellSticks = new ArrayList<>();

        Map<String, SellStick> sellStickMap = SellStickManager.getSellSticks();

        for (Map.Entry<String, SellStick> entry : sellStickMap.entrySet()) {
            SellStick sellStick = entry.getValue();
            ItemStack itemStack = new ItemStack(sellStick.getMaterial(), 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', sellStick.getName()));
            itemMeta.setLore(sellStick.getLore());
            itemStack.setItemMeta(itemMeta);

            sellSticks.add(itemStack);
        }

        for (int i = 0; i < sellSticks.size(); i++) {
            inventory.setItem(i + items.size(), sellSticks.get(i));
        }

        player.openInventory(inventory);
    }



}
