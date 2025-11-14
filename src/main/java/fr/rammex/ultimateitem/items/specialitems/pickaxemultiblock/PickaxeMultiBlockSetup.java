package fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock;

import fr.rammex.ultimateitem.utils.YamlFiles;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class PickaxeMultiBlockSetup {
    public static void setupItems() {
        FileConfiguration itemsConf = YamlFiles.getItemsConf();
        if (itemsConf == null) {
            System.out.println("Configuration file not loaded.");
            return;
        }

        if (!itemsConf.isConfigurationSection("pickaxe_multi_block")) {
            System.out.println("Section 'pickaxe_multi_block' not found in the configuration file.");
            return;
        }

        Set<String> itemKeys = itemsConf.getConfigurationSection("pickaxe_multi_block").getKeys(false);
        for (String key : itemKeys) {
            String id = key;
            String name = itemsConf.getString("pickaxe_multi_block." + key + ".Name", "Unknown Pickaxe");
            String materialName = itemsConf.getString("pickaxe_multi_block." + key + ".Material");
            Material material = Material.matchMaterial(materialName);

            if (material == null) {
                System.out.println("Invalid material for pickaxe_multi_block: " + key);
                continue;
            }

            int durability = itemsConf.getInt("pickaxe_multi_block." + key + ".Durability", 0);
            boolean dropable = itemsConf.getBoolean("pickaxe_multi_block." + key + ".Dropable", true);
            boolean keepOnDeath = itemsConf.getBoolean("pickaxe_multi_block." + key + ".KeepOnDeath", true);
            boolean indestructible = itemsConf.getBoolean("pickaxe_multi_block." + key + ".Indestructible", false);
            List<String> lore = itemsConf.getStringList("pickaxe_multi_block." + key + ".Lore");
            List<Integer> miningArea = itemsConf.getIntegerList("pickaxe_multi_block." + key + ".MiningArea");

            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    lore.set(i, lore.get(i).replace("&", "§"));
                }
            }

            PickaxeMultiBlock item = new PickaxeMultiBlock(id, name, material, durability, dropable, keepOnDeath, indestructible, lore, miningArea);
            PickaxeMultiBlockManager.addItem(item);
        }
    }
}
