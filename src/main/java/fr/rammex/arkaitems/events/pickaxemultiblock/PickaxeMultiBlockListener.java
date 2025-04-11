package fr.rammex.arkaitems.events.pickaxemultiblock;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import fr.rammex.arkaitems.items.specialitems.autoplaceblock.AutoPlaceBlock;
import fr.rammex.arkaitems.items.specialitems.pickaxemultiblock.PickaxeMultiBlock;
import fr.rammex.arkaitems.items.specialitems.pickaxemultiblock.PickaxeMultiBlockManager;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PickaxeMultiBlockListener implements Listener {
    @EventHandler
    public void onBlockMined(BlockBreakEvent event){
        if (event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().hasItemMeta()) {
            String itemName = event.getPlayer().getItemInHand().getItemMeta().getDisplayName();
            if (isPickaxeExistWithName(getItemName(itemName))) {
                event.setCancelled(true);
                PickaxeMultiBlock pickaxeMultiBlock = PickaxeMultiBlockManager.getItemByName(getItemName(itemName));
                if (pickaxeMultiBlock != null) {
                    List<Integer> miningArea = pickaxeMultiBlock.getMiningArea();
                    Location baseLocation = event.getBlock().getLocation();
                    if (miningArea != null && miningArea.size() == 3) {
                        mineArea(event.getPlayer(), baseLocation, miningArea);
                    } else {
                        event.getPlayer().sendMessage("Invalid mining area configuration. Expected 3 dimensions (x, y, z).");
                        return;
                    }
                    int pickaxeMultiBlockDurability = Integer.valueOf(pickaxeMultiBlock.getDurability());
                    if (pickaxeMultiBlockDurability > 0) {
                        updatePickaxeMultiBlock(event.getPlayer(), pickaxeMultiBlock, pickaxeMultiBlock.getId(), pickaxeMultiBlockDurability);
                    } else if (pickaxeMultiBlockDurability - 1 == 0 || pickaxeMultiBlockDurability == 0) {
                        event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
                    } else if (pickaxeMultiBlockDurability == -1){
                    }
                } else {
                    return;
                }
            }
        }
    }

    private void updatePickaxeMultiBlock(Player player, PickaxeMultiBlock pickaxeMultiBlock, String id, int Durability) {
        ItemStack itemStack = new ItemStack(pickaxeMultiBlock.getMaterial(), 1);
        itemStack = ItemMetadata.setMetadata(itemStack, "ID", id);
        itemStack = ItemMetadata.setMetadata(itemStack, "Durability", String.valueOf(Durability-1));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', pickaxeMultiBlock.getName()));
        itemMeta.setLore(pickaxeMultiBlock.getLore());
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), itemStack);
    }

    private boolean isPickaxeExistWithName(String name) {
        if (PickaxeMultiBlockManager.getItemByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String getItemName(String name) {
        return name.replace("§", "&");
    }

    private void mineArea(Player player, Location baseLocation, List<Integer> miningArea) {
        if (miningArea.size() != 3) {
            player.sendMessage("Invalid mining area configuration. Expected 3 dimensions (x, y, z).");
            return;
        }

        int radiusX = miningArea.get(0) / 2;
        int radiusY = miningArea.get(1) / 2;
        int radiusZ = miningArea.get(2) / 2;

        for (int x = -radiusX; x <= radiusX; x++) {
            for (int y = -radiusY; y <= radiusY; y++) {
                for (int z = -radiusZ; z <= radiusZ; z++) {
                    Location targetLocation = baseLocation.clone().add(x, y, z);
                    Material blockType = targetLocation.getBlock().getType();
                    if (blockType != Material.AIR && !checkFaction(player, targetLocation)) {
                        targetLocation.getBlock().setType(Material.AIR);

                        if (blockType != Material.AIR) {
                            targetLocation.getWorld().dropItemNaturally(targetLocation, new ItemStack(blockType));
                        }
                    }
                }
            }
        }
    }

    private boolean checkFaction(Player player, Location location) {
        // Vérification FactionsUUID
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            FPlayer fPlayer = com.massivecraft.factions.FPlayers.getInstance().getByPlayer(player);
            Faction playerFaction = fPlayer.getFaction();
            FLocation locationFLocation = new FLocation(location);
            Faction locationFaction = com.massivecraft.factions.Board.getInstance()
                    .getFactionAt(locationFLocation);

            if (!playerFaction.equals(locationFaction) && !locationFaction.isWilderness()) {
                return true;
            }
        }

        return false;
    }
}
