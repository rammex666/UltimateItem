package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CutTreeEffect implements Listener {

    @EventHandler
    public void onLogBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        ItemStack itemInHand = event.getPlayer().getInventory().getItemInHand();
        if(itemInHand == null || itemInHand.getType() == Material.AIR) {
            return;
        }
        String itemName = itemInHand.getItemMeta().getDisplayName();
        if(itemName == null || itemName.isEmpty()) {
            return;
        }

        if (isItemExistWithName(getItemName(itemName))) {
            Items item = ItemManager.getItemByName(getItemName(itemName));
            if (item != null && item.getCustomsEffects() != null) {
                Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                if (effectClass != null && effectClass == CutTreeEffect.class) {
                    if (isLog(block.getType()) && isPartOfTree(block)) {
                        breakTree(block);
                        ItemMetadata.updateDurability(itemInHand);
                    }
                }
            }
        }
    }

    private void breakTree(Block block) {
        Set<Block> visited = new HashSet<>();
        breakTreeRecursive(block, visited);
    }

    private void breakTreeRecursive(Block block, Set<Block> visited) {
        if (visited.contains(block)) {
            return;
        }

        visited.add(block);

        // Casse uniquement les blocs de type LOG
        if (isLog(block.getType())) {
            block.breakNaturally();
        }

        // Parcourt les blocs adjacents
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block adjacentBlock = block.getRelative(x, y, z);
                    if (!visited.contains(adjacentBlock) && isLog(adjacentBlock.getType())) {
                        breakTreeRecursive(adjacentBlock, visited);
                    }
                }
            }
        }
    }

    private boolean isPartOfTree(Block block) {
        for (int x = -5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -5; z <= 5; z++) {
                    Block nearbyBlock = block.getRelative(x, y, z);
                    if (isLeaf(nearbyBlock.getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isLog(Material material) {
        return material.name().startsWith("LOG");
    }

    private boolean isLeaf(Material material) {
        return material.name().startsWith("LEAVES");
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