package fr.rammex.arkaitems.events;

import fr.rammex.arkaitems.database.ItemMetaDataManager;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack item = e.getItem();
        if(item == null)return;
        if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
            if(!ItemMetadata.hasMetadata(item, "ID") && !ItemMetaDataManager.isItemInTable(ItemMetadata.getMetadata(item, "ID").toString())){
                String id = ItemMetaDataManager.getNextItemID();
                String ItemMeta = item.getItemMeta().toString();
                p.setItemInHand(ItemMetadata.setMetadata(item, "ID", id));
                ItemMetaDataManager.insertNewOwnerItem(p.getUniqueId(), ItemMeta);
                p.sendMessage(ChatColor.AQUA + ">> Added ID Metadata");
                return;
            }
            p.sendMessage(ChatColor.AQUA + ">> Already has the ID Metadata");
        }
        if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
            if(ItemMetadata.hasMetadata(item, "ID")){
                p.sendMessage(ChatColor.GREEN + ">> " + ItemMetadata.getMetadata(item, "ID"));
            }else{
                p.sendMessage(ChatColor.RED + ">> Hasn't the SomeText Metadata");
            }
        }
    }
}
