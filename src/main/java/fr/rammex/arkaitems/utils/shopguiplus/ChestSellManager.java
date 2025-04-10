package fr.rammex.arkaitems.utils.shopguiplus;

import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestSellManager {

    public static void sellChestContents(Player player, Chest chest, double multiplier) {
        Inventory chestInventory = chest.getBlockInventory();
        double totalSellPrice = 0.0;

        for (ItemStack item : chestInventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }

            double itemSellPrice = ShopGuiPlusApi.getItemStackPriceSell(player, item);

            if (itemSellPrice > 0) {
                totalSellPrice += itemSellPrice * multiplier;

                chestInventory.remove(item);
            }
        }

        if (totalSellPrice > 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "eco give " + player.getName() + " " + totalSellPrice);
            player.sendMessage("§aVous avez vendu le contenu du coffre pour §e" + totalSellPrice + "§a !");
        } else {
            player.sendMessage("§cAucun item vendable trouvé dans le coffre.");
        }
    }
}
