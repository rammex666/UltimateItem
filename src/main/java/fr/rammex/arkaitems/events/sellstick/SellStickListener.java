package fr.rammex.arkaitems.events.sellstick;

import fr.rammex.arkaitems.items.specialitems.sellstick.SellStickManager;
import fr.rammex.arkaitems.utils.ItemMetadata;
import fr.rammex.arkaitems.utils.shopguiplus.ChestSellManager;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SellStickListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) {
            return;
        }
        if (event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Chest) {
            if (isSellStickExistWithName(getSellStickName(event.getItem().getItemMeta().getDisplayName()))) {
                event.setCancelled(true);
                Double sellAmount = Double.valueOf(ItemMetadata.getMetadata(event.getItem(), "Multiplier"));
                if (sellAmount == null) {
                    event.getPlayer().sendMessage("§cError: Sell amount is null.");
                    return;
                }
                Chest chest = (Chest) event.getClickedBlock().getState();
                Player player = event.getPlayer();
                ChestSellManager.sellChestContents(player, chest, sellAmount);
                int sellStickDurability = Integer.valueOf(ItemMetadata.getMetadata(event.getItem(), "Durability"));
                System.out.println("Durability: " + sellStickDurability);
                if( sellStickDurability > 0) {
                    System.out.println("Durability: " + sellStickDurability);
                    ItemMetadata.setMetadata(event.getItem(), "Durability", String.valueOf(sellStickDurability - 1));
                    event.getPlayer().sendMessage("§aDurabilité de la baguette de vente: " + (sellStickDurability - 1));
                } else if (sellStickDurability-1 == 0){
                    event.getPlayer().getInventory().remove(event.getItem());
                    event.getPlayer().sendMessage("§cVotre baguette de vente est cassée.");
                } else if (sellStickDurability == -1){
                    System.out.println("Durability: " + sellStickDurability);
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
}
