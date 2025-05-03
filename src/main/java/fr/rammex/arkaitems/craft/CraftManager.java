package fr.rammex.arkaitems.craft;

import fr.rammex.arkaitems.items.ItemManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftManager {

    private static Map <String, Craft> craftMap = new HashMap<>();

    public static void addCraft(Craft craft) {
        craftMap.put(craft.getId(), craft);
    }

    public static Map<String, Craft> getCrafts() {
        return craftMap;
    }

    public static Craft getCraftById(String id) {
        return craftMap.get(id);
    }

    public static Craft getCraftByItemId(String id) {
        for (Craft craft : craftMap.values()) {
            if (craft.getItemId().equals(id)) {
                return craft;
            }
        }
        return null;
    }


    public static Craft getCraftByRecipie(List<String> recipe, Player player) {
        for (Craft craft : craftMap.values()) {
            List<String> craftRecipe = craft.getRecipe();

            // Vérification de la taille
            if (craftRecipe.size() == recipe.size()) {
                boolean match = true;

                // Vérification des patterns
                for (int i = 0; i < recipe.size(); i++) {
                    String input = recipe.get(i);
                    String expected = craftRecipe.get(i);

                    // Transformation des arkaitem en Items
                    if (expected.startsWith("arkaitem:")) {
                        String itemId = expected.split(":")[1];
                        expected = String.valueOf(ItemManager.getItemById(itemId));
                    } else {
                        // Vérification avec Material si ce n'est pas un arkaitem
                        try {
                            if (!"AIR".equals(input) && !input.equals(expected) && !input.equals(org.bukkit.Material.valueOf(expected).toString())) {
                                match = false;
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            // Si le Material n'existe pas, considérer comme non valide
                            match = false;
                            break;
                        }
                    }
                }

                // Vérification des requirements
                if (match && validateRequirements(craft.getRequire(), player)) {
                    return craft;
                }
            }
        }
        return null;
    }

    private static boolean validateRequirements(List<String> requirements, Player player) {
        for (String requirement : requirements) {
            if(requirement.startsWith("perm:")){
                String permission = requirement.split(":")[1];
                if (!player.hasPermission(permission)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int getCraftCount() {
        return craftMap.size();
    }

}
