package fr.rammex.arkaitems.events.autoplaceblock;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import fr.rammex.arkaitems.items.specialitems.autoplaceblock.AutoPlaceBlock;
import fr.rammex.arkaitems.items.specialitems.autoplaceblock.AutoPlaceBlockManager;
import fr.rammex.arkaitems.utils.ItemMetadata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static fr.rammex.arkaitems.utils.TimesTask.waitOneTick;

public class AutoPlaceBlockListener implements Listener {

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getItemInHand() != null && event.getItemInHand().hasItemMeta()) {
            String itemName = event.getItemInHand().getItemMeta().getDisplayName();
            if (isAutoPlaceBlockWithName(getAutoPlaceBlockName(itemName))) {
                event.setCancelled(true);
                AutoPlaceBlock autoPlaceBlock = AutoPlaceBlockManager.getItemByName(getAutoPlaceBlockName(itemName));
                if(autoPlaceBlock.isGetItemFromInventory()){
                    Material material = autoPlaceBlock.getBlockTypeFromInventory();
                    int amount = autoPlaceBlock.getTo() - autoPlaceBlock.getFrom();
                    if(player.getInventory().contains(material, amount)){
                        player.getInventory().removeItem(new ItemStack(material, amount));
                        waitOneTick(() -> placeBlock(player, autoPlaceBlock, event.getBlockPlaced().getLocation()));
                        if (autoPlaceBlock != null) {
                            int autoPlaceBlockDurability = Integer.valueOf(autoPlaceBlock.getDurability());
                            if (autoPlaceBlockDurability > 0) {
                                updateAutoPlaceBlock(player, autoPlaceBlock, autoPlaceBlock.getId(), autoPlaceBlockDurability);
                            } else if (autoPlaceBlockDurability - 1 == 0 || autoPlaceBlockDurability == 0) {
                                event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
                            } else if (autoPlaceBlockDurability == -1){
                            }
                        } else {
                            return;
                        }
                    } else {
                        player.sendMessage("You don't have enough items in your inventory.");
                        return;
                    }
                } else {
                    placeBlock(player, autoPlaceBlock, event.getBlockPlaced().getLocation());
                    if (autoPlaceBlock != null) {
                        int autoPlaceBlockDurability = Integer.valueOf(autoPlaceBlock.getDurability());
                        if (autoPlaceBlockDurability > 0) {
                            updateAutoPlaceBlock(player, autoPlaceBlock, autoPlaceBlock.getId(), autoPlaceBlockDurability);
                        } else if (autoPlaceBlockDurability - 1 == 0 || autoPlaceBlockDurability == 0) {
                            event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
                        } else if (autoPlaceBlockDurability == -1){
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private boolean isAutoPlaceBlockWithName(String name) {
        if (AutoPlaceBlockManager.getItemByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String getAutoPlaceBlockName(String name) {
        return name.replace("§", "&");
    }

    private void updateAutoPlaceBlock(Player player, AutoPlaceBlock autoPlaceBlock, String id, int Durability) {
        ItemStack itemStack = new ItemStack(autoPlaceBlock.getMaterial(), 1);
        itemStack = ItemMetadata.setMetadata(itemStack, "ID", id);
        itemStack = ItemMetadata.setMetadata(itemStack, "Durability", String.valueOf(Durability-1));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', autoPlaceBlock.getName()));
        itemMeta.setLore(autoPlaceBlock.getLore());
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), itemStack);
    }

    private void placeBlock(Player player, AutoPlaceBlock autoPlaceBlock, Location baseLocation) {
        int from = autoPlaceBlock.getFrom();
        int to = autoPlaceBlock.getTo();
        String direction = autoPlaceBlock.getDirection();
        Material material = autoPlaceBlock.getBlockTypeFromInventory();

        if (material != null) {
            if (!checkFaction(player, baseLocation)) {
                waitOneTick(() -> baseLocation.getBlock().setType(material));
            }

            float yaw = player.getLocation().getYaw();
            String cardinalDirection = getCardinalDirection(yaw);

            for (int i = from; i <= to; i++) {
                Location targetLocation = baseLocation.clone(); // Clone la location de base pour chaque itération
                if ("x".equalsIgnoreCase(direction)) {
                    switch (cardinalDirection) {
                        case "NORTH":
                            targetLocation.add(0, 0, i);
                            break;
                        case "SOUTH":
                            targetLocation.add(0, 0, -i);
                            break;
                        case "EAST":
                            targetLocation.add(-i, 0, 0);
                            break;
                        case "WEST":
                            targetLocation.add(i, 0, 0);
                            break;
                    }
                } else if ("y".equalsIgnoreCase(direction)) {
                    targetLocation.add(0, i, 0);
                } else {
                    player.sendMessage("Invalid direction.");
                    return;
                }

                // Vérifie si le bloc peut être placé
                if (targetLocation.getBlock().getType() != Material.AIR) {
                    continue;
                }
                if (checkFaction(player, targetLocation)) {
                    continue;
                }

                // Place le bloc
                targetLocation.getBlock().setType(material);
            }
        } else {
            player.sendMessage("Invalid block type.");
        }
    }

    private String getCardinalDirection(float yaw) {
        yaw = (yaw < 0) ? yaw + 360 : yaw;
        if (yaw >= 315 || yaw < 45) {
            return "NORTH";
        } else if (yaw >= 45 && yaw < 135) {
            return "EAST";
        } else if (yaw >= 135 && yaw < 225) {
            return "SOUTH";
        } else {
            return "WEST";
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
