package fr.rammex.ultimateitem.gui;

import fr.rammex.ultimateitem.craft.Craft;
import fr.rammex.ultimateitem.craft.CraftManager;
import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static fr.rammex.ultimateitem.utils.TimesTask.waitOneTick;

public class CustomCraftGUI implements Listener {

    private static boolean craftSuccess = false;

    public static void buildInventory(Player player, int size) {
        // Taille minimale de l'inventaire (grille de craft + 2 lignes supplémentaires)
        int minInventorySize = size * size + 2 * 9;

        // Arrondir au multiple de 9 supérieur
        int INVENTORY_SIZE = ((minInventorySize + 8) / 9) * 9;



        // Vérifier que l'inventaire ne dépasse pas 54 emplacements
        if (INVENTORY_SIZE > 54) {
            player.sendMessage("§cL'inventaire ne peut pas dépasser 54 emplacements.");
            return;
        }

        Inventory inventory = Bukkit.createInventory(player, INVENTORY_SIZE, "§8Craft Items");

        ItemStack grayGlass = new ItemStack(Material.BARRIER);
        ItemMeta meta = grayGlass.getItemMeta();
        meta.setDisplayName("§7");
        grayGlass.setItemMeta(meta);

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            inventory.setItem(i, grayGlass);
        }

        // Calculer les coordonnées de la grille de craft
        int startRow = (INVENTORY_SIZE / 9 - size) / 2; // Ligne de départ
        int startCol = (9 - size) / 2; // Colonne de départ

        // Placer la grille de craft
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int slot = (startRow + row) * 9 + (startCol + col);
                inventory.setItem(slot, null); // Emplacement vide pour le craft
            }
        }

        // Placer l'emplacement du résultat (par exemple, à droite de la grille)
        int resultSlot = (startRow + size / 2) * 9 + (startCol + size + 1);
        inventory.setItem(resultSlot, null); // Emplacement vide pour le résultat

        // Ouvrir l'inventaire pour le joueur
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§8Craft Items") && event.getClickedInventory() != event.getWhoClicked().getInventory()) {
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && currentItem.getType() == Material.BARRIER) {
                event.setCancelled(true);
            }

            // Recalculer dynamiquement resultSlot
            int INVENTORY_SIZE = event.getView().getTopInventory().getSize();
            int size = (int) Math.sqrt(INVENTORY_SIZE - 18);
            int startRow = (INVENTORY_SIZE / 9 - size) / 2;
            int startCol = (9 - size) / 2;
            int resultSlot = (startRow + size / 2) * 9 + (startCol + size + 1);

            // Gérer le clic sur l'emplacement du résultat
            int clickedSlot = event.getSlot();

            // Vérifier si un item est retiré ou ajouté
            waitOneTick(() -> {
                List<String> recipe = new ArrayList<>();

                for (int row = 0; row < size; row++) {
                    for (int col = 0; col < size; col++) {
                        int craftSlot = (startRow + row) * 9 + (startCol + col);
                        ItemStack item = event.getView().getTopInventory().getItem(craftSlot);

                        if (item == null || item.getType() == Material.AIR) {
                            recipe.add("AIR");
                        } else {
                            // Vérifie si c'est un Items personnalisé
                            if (item.getItemMeta() != null && item.getItemMeta().hasDisplayName()) {
                                String itemName = item.getItemMeta().getDisplayName();
                                Items customItem = ItemManager.getItemByName(getItemName(itemName));
                                if (customItem != null) {
                                    recipe.add(("arkaitem:" + customItem.getId()).toUpperCase());
                                } else {
                                    recipe.add(item.getType().toString());
                                }
                            } else {
                                recipe.add(item.getType().toString());
                            }
                        }
                    }
                }

                // Vérification stricte de la recette
                Craft matchingCraft = CraftManager.getCraftByRecipie(recipe, (Player) event.getWhoClicked());
                Player player = (Player) event.getWhoClicked();

                // Si la recette ne correspond plus, retirer l'item du slot résultat
                if (matchingCraft == null || !matchingCraft.getRecipe().equals(recipe)) {
                    event.getClickedInventory().setItem(resultSlot, null);
                    craftSuccess = false;
                } else if (clickedSlot != resultSlot) {
                    // Si un item est ajouté ou retiré, recalculer le résultat
                    Items resultItem = ItemManager.getItemById(matchingCraft.getItemId());
                    if (resultItem != null) {
                        ItemStack result = ItemManager.createItem(player, matchingCraft.getItemId(), 1);
                        if (result != null) {
                            event.getClickedInventory().setItem(resultSlot, result);
                            craftSuccess = true;
                        }
                    }
                }
            });

            if (clickedSlot == resultSlot) {
                ItemStack cursorItem = event.getCursor();

                // Si le joueur essaie de placer un item dans le resultSlot
                if (cursorItem != null && cursorItem.getType() != Material.AIR) {
                    event.setCancelled(true);
                    return;
                }

                if (event.getClickedInventory().getItem(resultSlot) != null && craftSuccess) {
                    for (int row = 0; row < size; row++) {
                        for (int col = 0; col < size; col++) {
                            int craftSlot = (startRow + row) * 9 + (startCol + col);
                            event.getClickedInventory().setItem(craftSlot, null);
                        }
                    }

                    Player player = (Player) event.getWhoClicked();
                    player.updateInventory(); // Réinitialiser la grille de craft

                    String itemName = event.getClickedInventory().getItem(resultSlot).getItemMeta().getDisplayName();

                    Craft matchingCraft = CraftManager.getCraftByItemId(ItemManager.getItemByName(getItemName(itemName)).getId());
                    if (matchingCraft != null) {
                        List<String> commandsSuccess = matchingCraft.getCommandsSuccess();
                        if (commandsSuccess != null) {
                            for (String command : commandsSuccess) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                            }
                        }
                    }
                }
            }
        }
    }
    private String getItemName(String name) {
        return name.replace("§", "&");
    }
}