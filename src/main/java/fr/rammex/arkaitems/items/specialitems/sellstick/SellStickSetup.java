package fr.rammex.arkaitems.items.specialitems.sellstick;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.utils.YamlFiles;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class SellStickSetup {
    public static void setupItems(){
        FileConfiguration itemsConf = YamlFiles.getItemsConf();
        ItemManager.resetItems();

        Set<String> itemKeys = itemsConf.getConfigurationSection("sellsticks").getKeys(false);
        for(String key : itemKeys){
            String id = key;
            String name = itemsConf.getString("sellsticks." + key + ".Name");
            Material material = Material.valueOf(itemsConf.getString("sellsticks." + key + ".Material"));
            if(material == null){
                System.out.println("Material " + itemsConf.getString("sellsticks." + key + ".Material") + " not found");
                continue;
            }
            int durability = itemsConf.getInt("sellsticks." + key + ".Durability");
            float sellMultiplier = (float) itemsConf.getDouble("sellsticks." + key + ".SellMultiplier");
            boolean dropable = itemsConf.getBoolean("sellsticks." + key + ".Dropable");
            boolean keepOnDeath = itemsConf.getBoolean("sellsticks." + key + ".KeepOnDeath");
            boolean indestructible = itemsConf.getBoolean("sellsticks." + key + ".Indestructible");
            List<String> lore = itemsConf.getStringList("sellsticks." + key + ".Lore");
            if(lore !=null){
                for(int i = 0; i < lore.size(); i++){
                    lore.set(i, lore.get(i).replace("&", "§"));
                }
            }

            SellStick item = new SellStick(id, name, material, sellMultiplier, durability, dropable, keepOnDeath, indestructible, lore);
            SellStickManager.addSellStick(item);
        }
    }
}
