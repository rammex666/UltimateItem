package fr.rammex.arkaitems.utils;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ItemMetadata {

    public static org.bukkit.inventory.ItemStack setMetadata(org.bukkit.inventory.ItemStack item, String key, String value) {
        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem == null) {
            return item;
        }
        if (nmsItem.getTag() == null) {
            nmsItem.setTag(new NBTTagCompound());
        }
        nmsItem.getTag().setString(key, value);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean hasMetadata(org.bukkit.inventory.ItemStack item, String key) {
        if (item == null) {
            return false;
        }
        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return nmsItem != null && nmsItem.getTag() != null && nmsItem.getTag().hasKey(key);
    }

    public static String getMetadata(org.bukkit.inventory.ItemStack item, String key) {
        if (item == null) {
            return null;
        }
        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem != null && nmsItem.getTag() != null && nmsItem.getTag().hasKey(key)) {
            return nmsItem.getTag().getString(key);
        }
        return null;
    }
}