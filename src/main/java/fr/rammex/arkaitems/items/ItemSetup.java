package fr.rammex.arkaitems.items;

import fr.rammex.arkaitems.effects.CustomsEffects;
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
            String materialName = itemsConf.getString("items." + key + ".Material");
            Material material = Material.matchMaterial(materialName);
            CustomsEffects customEffect = CustomsEffects.match(itemsConf.getString("items." + key + ".CustomEffect"));
            if (customEffect == null) {
                System.out.println("Invalid custom effect for item: " + key);
                continue;
            }

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

            List<String> typesCommandsOnEvent;
            List<String> commandsOnYou;
            List<String> commandsOnEnemy;

            List<String> enchantsOnEquip = itemsConf.getStringList("items." + key + ".Enchantments_On_Equip");
            List<String> enchantsAssociated = itemsConf.getStringList("items." + key + ".Enchantments_Associated");
            if(itemsConf.get("items." + key + ".Commands_On_Event") != null) {
                typesCommandsOnEvent = itemsConf.getStringList("items." + key + ".Commands_On_Event.type");
                commandsOnYou = itemsConf.getStringList("items." + key + ".Commands_On_Event.CommandsOnYou");
                commandsOnEnemy = itemsConf.getStringList("items." + key + ".Commands_On_Event.CommandsOnEnemy");
                for (int i = 0; i < commandsOnYou.size(); i++) {
                    commandsOnYou.set(i, commandsOnYou.get(i).replace("&", "§"));
                }
                for (int i = 0; i < commandsOnEnemy.size(); i++) {
                    commandsOnEnemy.set(i, commandsOnEnemy.get(i).replace("&", "§"));
                }
            } else {
                typesCommandsOnEvent = null;
                commandsOnYou = null;
                commandsOnEnemy = null;
            }

            Items item = new Items(id, name, material, customEffect, amount, dropable, keepOnDeath, dropPlayerHead, indestructible, lore, enchantsOnEquip, enchantsAssociated, typesCommandsOnEvent, commandsOnYou, commandsOnEnemy);
            ItemManager.addItem(item);
        }
    }
}