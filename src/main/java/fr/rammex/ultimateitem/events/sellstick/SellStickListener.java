package fr.rammex.ultimateitem.events.sellstick;

import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStick;
import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStickManager;
import fr.rammex.ultimateitem.utils.ItemMetadata;
import fr.rammex.ultimateitem.utils.shopguiplus.ChestSellManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SellStickListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) {
            return;
        }
        if (event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Chest) {
            if (event.getItem().getItemMeta() == null || !event.getItem().getItemMeta().hasDisplayName()) {
                return;
            }
            if (isSellStickExistWithName(getSellStickName(event.getItem().getItemMeta().getDisplayName()))) {
                event.setCancelled(true);
                Double sellAmount = Double.valueOf(ItemMetadata.getMetadata(event.getItem(), "Multiplier"));
                if (sellAmount == null) {
                    return;
                }
                Chest chest = (Chest) event.getClickedBlock().getState();
                Player player = event.getPlayer();
                ChestSellManager.sellChestContents(player, chest, sellAmount);
                int sellStickDurability = Integer.valueOf(ItemMetadata.getMetadata(event.getItem(), "Durability"));
                if (sellStickDurability > 0) {
                    SellStick sellStick = SellStickManager.getSellStickByName(getSellStickName(event.getItem().getItemMeta().getDisplayName()));
                    updateSellStick(player, sellStick, ItemMetadata.getMetadata(event.getItem(), "ID"), sellStickDurability);
                } else if (sellStickDurability - 1 == 0 || sellStickDurability == 0) {
                    event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
                } else if (sellStickDurability == -1){
                }
            } else {
                return;
            }
        }
    }

    private boolean isSellStickExistWithName(String name) {
        if (SellStickManager.getSellStickByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String getSellStickName(String name) {
        return name.replace("§", "&");
    }

    private void updateSellStick(Player player, SellStick sellStick, String id, int Durability) {
        ItemStack itemStack = new ItemStack(sellStick.getMaterial(), 1);
        itemStack = ItemMetadata.setMetadata(itemStack, "ID", id);
        itemStack = ItemMetadata.setMetadata(itemStack, "Multiplier", String.valueOf(sellStick.getSellMultiplier()));
        itemStack = ItemMetadata.setMetadata(itemStack, "Durability", String.valueOf(Durability-1));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', sellStick.getName()));
        itemMeta.setLore(sellStick.getLore());
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), itemStack);
    }
}
