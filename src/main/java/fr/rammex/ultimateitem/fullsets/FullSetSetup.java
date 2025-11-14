package fr.rammex.ultimateitem.fullsets;

import fr.rammex.ultimateitem.utils.YamlFiles;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class FullSetSetup {

    public static void setupFullSets() {
        FileConfiguration itemsConf = YamlFiles.getItemsConf();
        if (itemsConf == null) {
            System.out.println("Configuration file not loaded.");
            return;
        }

        if (!itemsConf.isConfigurationSection("fullsets")) {
            System.out.println("Section 'fullsets' not found in the configuration file.");
            return;
        }

        Set<String> itemKeys = itemsConf.getConfigurationSection("fullsets").getKeys(false);
        for (String key : itemKeys) {
            String id = key;
            String name = itemsConf.getString("fullsets." + key + ".Name", "Unknown Set");
            List<String> items = itemsConf.getStringList("fullsets." + key + ".Set_Items");
            List<String> effects = itemsConf.getStringList("fullsets." + key + ".Full_Set_Bonus");

            FullSet fullSet = new FullSet(id, name, items, effects);
            FullSetManager.addSet(fullSet);
        }
    }
}
