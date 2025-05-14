package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.ArkaItems;
import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.utils.ItemMetadata;
import net.brcdev.shopgui.event.ShopPreTransactionEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class MaskFarmEffect implements Listener {

    double PRICE_MULTIPLIER = ArkaItems.instance.getConfig().getDouble("custom-effect.mask-farm.price-multiplier");;

    @EventHandler
    public void onShopPreTransaction(ShopPreTransactionEvent event) {
        if(hasRequiredEffect(event.getPlayer())) {
            double price = event.getPrice();
            double newPrice = price * PRICE_MULTIPLIER;
            event.setPrice(newPrice);
            ItemMetadata.updateDurability(event.getPlayer().getInventory().getHelmet());
        }
    }


    private boolean hasRequiredEffect(Player player) {
        ItemStack itemInHand = player.getInventory().getHelmet();
        if(itemInHand == null || itemInHand.getType() == Material.AIR) {
            return false;
        }
        String itemName = itemInHand.getItemMeta().getDisplayName();

        if (isItemExistWithName(getItemName(itemName))) {
            Items item = ItemManager.getItemByName(getItemName(itemName));
            if (item != null && item.getCustomsEffects() != null) {
                Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                if (effectClass != null && effectClass == MaskFarmEffect.class) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private String getItemName(String name) {
        return name.replace("§", "&");
    }
    private boolean isItemExistWithName(String name) {
        if (ItemManager.getItemByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }
}
