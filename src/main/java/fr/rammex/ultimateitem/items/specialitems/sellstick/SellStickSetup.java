package fr.rammex.ultimateitem.items.specialitems.sellstick;

import fr.rammex.ultimateitem.utils.YamlFiles;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class SellStickSetup {
    public static void setupItems() {
        FileConfiguration itemsConf = YamlFiles.getItemsConf();
        if (itemsConf == null) {
            System.out.println("Configuration file not loaded.");
            return;
        }

        if (!itemsConf.isConfigurationSection("sellsticks")) {
            System.out.println("Section 'sellsticks' not found in the configuration file.");
            return;
        }

        Set<String> itemKeys = itemsConf.getConfigurationSection("sellsticks").getKeys(false);
        for (String key : itemKeys) {
            String id = key;
            String name = itemsConf.getString("sellsticks." + key + ".Name", "Unknown Sell Stick");
            String materialName = itemsConf.getString("sellsticks." + key + ".Material");
            Material material = Material.matchMaterial(materialName);

            if (material == null) {
                System.out.println("Invalid material for sellstick: " + key);
                continue;
            }

            int durability = itemsConf.getInt("sellsticks." + key + ".Durability", 0);
            float sellMultiplier = (float) itemsConf.getDouble("sellsticks." + key + ".SellMultiplier", 1.0);
            boolean dropable = itemsConf.getBoolean("sellsticks." + key + ".Dropable", true);
            boolean keepOnDeath = itemsConf.getBoolean("sellsticks." + key + ".KeepOnDeath", true);
            boolean indestructible = itemsConf.getBoolean("sellsticks." + key + ".Indestructible", false);
            List<String> lore = itemsConf.getStringList("sellsticks." + key + ".Lore");

            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    lore.set(i, lore.get(i).replace("&", "§"));
                }
            }

            SellStick item = new SellStick(id, name, material, sellMultiplier, durability, dropable, keepOnDeath, indestructible, lore);
            SellStickManager.addSellStick(item);
        }
    }
}
