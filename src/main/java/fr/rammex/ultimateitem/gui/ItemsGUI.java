package fr.rammex.ultimateitem.gui;

import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.Items;
import fr.rammex.ultimateitem.items.specialitems.autoplaceblock.AutoPlaceBlock;
import fr.rammex.ultimateitem.items.specialitems.autoplaceblock.AutoPlaceBlockManager;
import fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock.PickaxeMultiBlock;
import fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock.PickaxeMultiBlockManager;
import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStick;
import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStickManager;
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

    public static void buildInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "§8[§6UltimateItem§8]");

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

        List<ItemStack> autoPlaceBlock = new ArrayList<>();
        Map<String, AutoPlaceBlock> autoPlaceBlockMap = AutoPlaceBlockManager.getItems();

        for (Map.Entry<String, AutoPlaceBlock> entry : autoPlaceBlockMap.entrySet()) {
            AutoPlaceBlock autoplaceblock = entry.getValue();
            ItemStack itemStack = new ItemStack(autoplaceblock.getMaterial(), 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', autoplaceblock.getName()));
            itemMeta.setLore(autoplaceblock.getLore());
            itemStack.setItemMeta(itemMeta);

            autoPlaceBlock.add(itemStack);
        }

        for (int i = 0; i < autoPlaceBlock.size(); i++) {
            inventory.setItem(i + items.size() + sellSticks.size(), autoPlaceBlock.get(i));
        }

        List<ItemStack> pickaxeMultiBlock = new ArrayList<>();
        Map<String, PickaxeMultiBlock> pickaxeMultiBlockMap = PickaxeMultiBlockManager.getItems();

        for (Map.Entry<String, PickaxeMultiBlock> entry : pickaxeMultiBlockMap.entrySet()) {
            PickaxeMultiBlock pickaxeMultiBlocks = entry.getValue();
            ItemStack itemStack = new ItemStack(pickaxeMultiBlocks.getMaterial(), 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', pickaxeMultiBlocks.getName()));
            itemMeta.setLore(pickaxeMultiBlocks.getLore());
            itemStack.setItemMeta(itemMeta);

            pickaxeMultiBlock.add(itemStack);
        }

        for (int i = 0; i < pickaxeMultiBlock.size(); i++) {
            inventory.setItem(i + items.size() + sellSticks.size() + autoPlaceBlock.size(), pickaxeMultiBlock.get(i));
        }

        player.openInventory(inventory);
    }



}
