package fr.rammex.arkaitems.events;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import fr.rammex.arkaitems.items.specialitems.autoplaceblock.AutoPlaceBlockManager;
import fr.rammex.arkaitems.items.specialitems.pickaxemultiblock.PickaxeMultiBlockManager;
import fr.rammex.arkaitems.items.specialitems.sellstick.SellStickManager;
import me.albert.skullapi.SkullAPI;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemListener implements Listener {

    private final Map<Player, List<ItemStack>> itemsToReturn = new HashMap<>();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if (event.getItemDrop().getItemStack().hasItemMeta()) {
            String itemName = event.getItemDrop().getItemStack().getItemMeta().getDisplayName();
            if(!checkDropable(getItemName(itemName))){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        if (event.isShiftClick() && currentItem != null && currentItem.hasItemMeta()) {
            String itemName = currentItem.getItemMeta().getDisplayName();
            if (isItemExistWithName(getItemName(itemName))) {
                Items item = ItemManager.getItemByName(getItemName(itemName));
                List<String> effectsOnEquip = item.getEnchantsOnEquip();
                if (effectsOnEquip != null) {
                    applyPotionEffectsFromConfig(player, effectsOnEquip);
                }
            }
        }

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            // Adding armor
            if (cursorItem != null && cursorItem.hasItemMeta()) {
                String itemName = cursorItem.getItemMeta().getDisplayName();
                if (isItemExistWithName(getItemName(itemName))) {
                    Items item = ItemManager.getItemByName(getItemName(itemName));
                    List<String> effectsOnEquip = item.getEnchantsOnEquip();
                    if (effectsOnEquip != null) {
                        applyPotionEffectsFromConfig(player, effectsOnEquip);
                    }
                }
            }

            if (currentItem != null && currentItem.hasItemMeta()) {
                String itemName = currentItem.getItemMeta().getDisplayName();
                if (isItemExistWithName(getItemName(itemName))) {
                    Items item = ItemManager.getItemByName(getItemName(itemName));
                    List<String> effectsOnEquip = item.getEnchantsOnEquip();
                    if (effectsOnEquip != null) {
                        removePotionEffectsFromConfig(player, effectsOnEquip);
                    }
                }
            }
        }
    }



    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<ItemStack> drops = event.getDrops();
        if (drops == null || drops.isEmpty()) {
            return;
        }

        List<ItemStack> itemsToKeep = new ArrayList<>();
        for (ItemStack item : drops) {
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String itemName = item.getItemMeta().getDisplayName();
                if (checkKeepOnDeath(getItemName(itemName))) {
                    itemsToKeep.add(item);
                }
            }
        }

        drops.removeAll(itemsToKeep);
        itemsToReturn.put(player, itemsToKeep);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (itemsToReturn.containsKey(player)) {
            List<ItemStack> items = itemsToReturn.remove(player);
            for (ItemStack item : items) {
                player.getInventory().addItem(item);
                player.sendMessage("§cVous avez récupéré votre " + item.getItemMeta().getDisplayName() + " après votre mort.");
            }
        }
    }


    @EventHandler
    public void onDurabilityChange(PlayerItemDamageEvent event){
        if (event.getItem().hasItemMeta()) {
            String itemName = event.getItem().getItemMeta().getDisplayName();
            if (checkIsIndestructible(getItemName(itemName))) {
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerGetKilledByPlayer(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();
            Player player = event.getEntity();
            if (killer.getInventory().getItemInHand() != null && killer.getInventory().getItemInHand().hasItemMeta()) {
                String itemName = killer.getInventory().getItemInHand().getItemMeta().getDisplayName();
                if (isItemExistWithName(getItemName(itemName))) {
                    Items item = ItemManager.getItemByName(getItemName(itemName));
                    if(item.isDropPlayerHead()){
                        ItemStack skull = SkullAPI.getSkull(player.getName());
                        ItemMeta skullMeta = skull.getItemMeta();

                        if (skullMeta != null) {
                            skullMeta.setDisplayName("Tête de " + player.getName()); // Nom personnalisé
                            skull.setItemMeta(skullMeta);
                        }
                        killer.getInventory().addItem(skull);
                    }
                }
            }
        }
    }




    private boolean isItemExistWithName(String name) {
        if (ItemManager.getItemByName(name) != null) {
            return true;}
        else {
            return false;
        }
    }

    private String getItemName(String name) {
        return name.replace("§", "&");
    }


    private boolean checkDropable(String name){
        if (ItemManager.getItemByName(name) != null) {
            return ItemManager.getItemByName(name).isDropable();
        } else if (SellStickManager.getSellStickByName(name) != null) {
            return SellStickManager.getSellStickByName(name).isDropable();
        } else if (AutoPlaceBlockManager.getItemByName(name) != null) {
            return AutoPlaceBlockManager.getItemByName(name).isDropable();
        } else if (PickaxeMultiBlockManager.getItemByName(name) != null) {
            return PickaxeMultiBlockManager.getItemByName(name).isDropable();
        } else {
            return false;
        }
    }

    private boolean checkKeepOnDeath(String name){
        if (ItemManager.getItemByName(name) != null) {
            return ItemManager.getItemByName(name).isKeepOnDeath();
        } else if (SellStickManager.getSellStickByName(name) != null) {
            return SellStickManager.getSellStickByName(name).isKeepOnDeath();
        } else if (AutoPlaceBlockManager.getItemByName(name) != null) {
            return AutoPlaceBlockManager.getItemByName(name).isKeepOnDeath();
        } else if (PickaxeMultiBlockManager.getItemByName(name) != null) {
            return PickaxeMultiBlockManager.getItemByName(name).isKeepOnDeath();
        } else {
            return false;
        }
    }

    private boolean checkIsIndestructible(String name){
        if (ItemManager.getItemByName(name) != null) {
            return ItemManager.getItemByName(name).isIndestructible();
        } else if (SellStickManager.getSellStickByName(name) != null) {
            return SellStickManager.getSellStickByName(name).isIndestructible();
        } else if (AutoPlaceBlockManager.getItemByName(name) != null) {
            return AutoPlaceBlockManager.getItemByName(name).isIndestructible();
        } else if (PickaxeMultiBlockManager.getItemByName(name) != null) {
            return PickaxeMultiBlockManager.getItemByName(name).isIndestructible();
        } else {
            return false;
        }
    }

    public static void applyPotionEffectsFromConfig(Player player, List<String> effects) {
        for (String effectEntry : effects) {

            String[] parts = effectEntry.split(":");
            if (parts.length == 2) {
                String effectName = parts[0];
                int level;
                try {
                    level = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid potion effect level: " + parts[1]);
                    continue;
                }
                PotionEffectType effectType = PotionEffectType.getByName(effectName.toUpperCase());
                if (effectType != null) {
                    player.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, level - 1));
                } else {
                    System.out.println("Potion effect not found: " + effectName);
                }
            }
        }
    }

    public static void removePotionEffectsFromConfig(Player player, List<String> effects) {
        for (String effectEntry : effects) {
            String[] parts = effectEntry.split(":");
            if (parts.length == 2) {
                String effectName = parts[0];
                PotionEffectType effectType = PotionEffectType.getByName(effectName.toUpperCase());
                if (effectType != null) {
                    player.removePotionEffect(effectType);
                } else {
                    System.out.println("Potion effect not found: " + effectName);
                }
            }
        }
    }
}
