package fr.rammex.ultimateitem.items.specialitems.autoplaceblock;

import fr.rammex.ultimateitem.utils.YamlFiles;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class AutoPlaceBlockSetup {
    public static void setupItems() {
        FileConfiguration itemsConf = YamlFiles.getItemsConf();
        if (itemsConf == null) {
            System.out.println("Configuration file not loaded.");
            return;
        }

        if (!itemsConf.isConfigurationSection("auto_place_block")) {
            System.out.println("Section 'auto_place_block' not found in the configuration file.");
            return;
        }

        Set<String> itemKeys = itemsConf.getConfigurationSection("auto_place_block").getKeys(false);
        for (String key : itemKeys) {
            String id = key;
            String name = itemsConf.getString("auto_place_block." + key + ".Name", "Unknown Sell Stick");
            String materialName = itemsConf.getString("auto_place_block." + key + ".Material");
            String direction = itemsConf.getString("auto_place_block." + key + ".direction", "y");
            Material material = Material.matchMaterial(materialName);

            if (material == null) {
                System.out.println("Invalid material for auto_place_block: " + key);
                continue;
            }

            int durability = itemsConf.getInt("auto_place_block." + key + ".Durability", 0);
            int from = itemsConf.getInt("auto_place_block." + key + ".from", 0);
            int to = itemsConf.getInt("auto_place_block." + key + ".to", 256);
            String blockTypeFromInventoryName = itemsConf.getString("auto_place_block." + key + ".blockTypeFromInventory");
            Material blockTypeFromInventory = Material.matchMaterial(blockTypeFromInventoryName);
            if (blockTypeFromInventory == null) {
                System.out.println("Invalid block type from inventory for auto_place_block: " + key);
                continue;
            }
            boolean getItemFromInventory = itemsConf.getBoolean("auto_place_block." + key + ".getBlockFromInventory", false);
            boolean dropable = itemsConf.getBoolean("auto_place_block." + key + ".Dropable", true);
            boolean keepOnDeath = itemsConf.getBoolean("auto_place_block." + key + ".KeepOnDeath", true);
            boolean indestructible = itemsConf.getBoolean("auto_place_block." + key + ".Indestructible", false);
            List<String> lore = itemsConf.getStringList("auto_place_block." + key + ".Lore");

            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    lore.set(i, lore.get(i).replace("&", "§"));
                }
            }

            AutoPlaceBlock item = new AutoPlaceBlock(id, name, direction, material, blockTypeFromInventory, durability, from, to, dropable, keepOnDeath, getItemFromInventory, indestructible, lore);
            AutoPlaceBlockManager.addItem(item);
        }
    }
}
