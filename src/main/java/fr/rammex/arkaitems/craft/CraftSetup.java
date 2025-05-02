package fr.rammex.arkaitems.craft;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.utils.YamlFiles;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class CraftSetup {
    public static void setupCrafts() {
        FileConfiguration craftConf = YamlFiles.getCraftsConf();
        if (craftConf == null) {
            System.out.println("Configuration file not loaded.");
            return;
        }

        if (!craftConf.isConfigurationSection("crafts")) {
            System.out.println("Section 'crafts' not found in the configuration file.");
            return;
        }

        Set<String> itemKeys = craftConf.getConfigurationSection("crafts").getKeys(false);
        for (String key : itemKeys) {
            System.out.println("Craft key: " + key);
            String id = key;
            String itemId = craftConf.getString("crafts." + key + ".itemId", "Unknown Craft");
            if (ItemManager.getItemById(itemId) == null) {
                System.out.println("Item " + itemId + " not found for craft: " + key);
                continue;
            }
            int amount = craftConf.getInt("crafts." + key + ".amount", 1);
            List<String> require = craftConf.getStringList("crafts." + key + ".require");
            List<String> shape = craftConf.getStringList("crafts." + key + ".recipe.shape");
            Map<String, String> ingredientsMap = new HashMap<>();
            craftConf.getConfigurationSection("crafts." + key + ".recipe.ingredients").getKeys(false)
                    .forEach(ingredientKey -> {
                        ingredientsMap.put(ingredientKey, craftConf.getString("crafts." + key + ".recipe.ingredients." + ingredientKey).toUpperCase());
                    });
            System.out.println("Ingredients map: " + ingredientsMap);
            List<String> recipeList = new ArrayList<>();
            for (String row : shape) {
                System.out.println("Row: " + row);
                for (char ingredientKey : row.toCharArray()) { // Iterate over each character in the row
                    String keyO = String.valueOf(ingredientKey);
                    if (keyO.equals("0")) {
                        System.out.println("Adding AIR");
                        recipeList.add("AIR");
                    } else if (ingredientsMap.containsKey(keyO)) {
                        String ingredient = ingredientsMap.get(keyO);
                        if (ingredient.startsWith("arkaitem")) {
                            System.out.println("Adding arkaitem: " + ingredient);
                        } else {
                            System.out.println("Adding ingredient: " + ingredient);
                        }
                        recipeList.add(ingredient);
                    } else {
                        recipeList.add("AIR");
                    }
                }
            }

            // Create and register the craft
            Craft craft = new Craft(id, itemId, amount, recipeList, require);
            CraftManager.addCraft(craft);
        }
    }
}