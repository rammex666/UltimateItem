package fr.rammex.arkaitems.effects.custom;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerRemoverEffect implements Listener {

    @EventHandler
    public void onSpawnerRemove(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        String itemName = itemInHand.getItemMeta().getDisplayName();

        if (isItemExistWithName(getItemName(itemName))) {
            Items item = ItemManager.getItemByName(getItemName(itemName));
            if (item != null && item.getCustomsEffects() != null) {
                Class<?> effectClass = item.getCustomsEffects().getEffectClass();
                if (effectClass != null && effectClass == SpawnerRemoverEffect.class) {
                    if (isSpawner(blockType)) {
                        BlockState blockState = event.getBlock().getState();
                        if (blockState instanceof CreatureSpawner) {
                            CreatureSpawner spawner = (CreatureSpawner) blockState;
                            String entityType = spawner.getSpawnedType().name();

                            event.setCancelled(true);
                            event.getBlock().setType(Material.AIR);

                            ItemStack spawnerItem = new ItemStack(Material.SPAWNER, 1);
                            spawnerItem = ItemMetadata.setMetadata(spawnerItem, "EntityType", entityType);

                            event.getPlayer().getInventory().addItem(spawnerItem);
                        }
                    }
                }
            }
        }
    }


    private boolean isSpawner(Material material) {
        return material == Material.SPAWNER;
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
