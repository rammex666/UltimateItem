package fr.rammex.arkaitems.items;

import fr.rammex.arkaitems.utils.YamlFiles;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class ItemSetup {

    public static void setupItems() {
        FileConfiguration itemsConf = YamlFiles.getItemsConf();
        if (itemsConf == null) {
            System.out.println("Configuration file not loaded.");
            return;
        }

        ItemManager.resetItems();

        if (!itemsConf.isConfigurationSection("items")) {
            System.out.println("Section 'items' not found in the configuration file.");
            return;
        }

        Set<String> itemKeys = itemsConf.getConfigurationSection("items").getKeys(false);
        for (String key : itemKeys) {
            String id = key;
            String name = itemsConf.getString("items." + key + ".Name", "Unknown Item");
            String fullSetName = itemsConf.getString("items." + key + ".fullSet", "none");
            String materialName = itemsConf.getString("items." + key + ".Material");
            Material material = Material.matchMaterial(materialName);

            if (material == null) {
                System.out.println("Invalid material for item: " + key);
                continue;
            }

            int amount = itemsConf.getInt("items." + key + ".Amount", 1); // Default to 1 if missing
            boolean dropable = itemsConf.getBoolean("items." + key + ".Dropable", true);
            boolean keepOnDeath = itemsConf.getBoolean("items." + key + ".KeepOnDeath", true);
            boolean dropPlayerHead = itemsConf.getBoolean("items." + key + ".DropPlayerHead", false);
            boolean indestructible = itemsConf.getBoolean("items." + key + ".Indestructible", false);
            List<String> lore = itemsConf.getStringList("items." + key + ".Lore");

            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    lore.set(i, lore.get(i).replace("&", "§"));
                }
            }

            List<String> enchantsOnEquip = itemsConf.getStringList("items." + key + ".Enchantments_On_Equip");
            List<String> fullSetBonus = itemsConf.getStringList("items." + key + ".Full_Set_Bonus");
            List<String> enchantsAssociated = itemsConf.getStringList("items." + key + ".Enchantments_Associated");
            List<String> commandsOnEvent = itemsConf.getStringList("items." + key + ".Commands_On_Equip");

            Items item = new Items(id, name, fullSetName, material, amount, dropable, keepOnDeath, dropPlayerHead, indestructible, lore, enchantsOnEquip, fullSetBonus, enchantsAssociated, commandsOnEvent);
            ItemManager.addItem(item);
        }
    }
}