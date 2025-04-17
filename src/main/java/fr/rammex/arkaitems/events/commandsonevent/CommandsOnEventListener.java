package fr.rammex.arkaitems.events.commandsonevent;

import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.Random;

import static org.bukkit.entity.EntityType.PLAYER;

public class CommandsOnEventListener implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) {
            return;
        }

        if (event.getItem().hasItemMeta()) {
            String itemName = event.getItem().getItemMeta().getDisplayName();
            if (isItemExistWithName(getItemName(itemName))) {
                Items item = ItemManager.getItemByName(getItemName(itemName));
                if (item.getTypesCommandOnEvent() != null) {
                    List<String> typesCommandOnEvent = item.getTypesCommandOnEvent();
                    List<String> commandsOnYou = item.getCommandsOnYou();
                    List<String> commandsOnEnemy = item.getCommandsOnEnemy();

                    for (String type : typesCommandOnEvent) {
                        if (type.equalsIgnoreCase("onuse")) {
                            for (String command : commandsOnYou) {
                                if (command != null && !command.isEmpty()) {
                                    decodeCommand(command, event.getPlayer());
                                }
                            }
                            Player nearestPlayer = getNearestPlayer(event.getPlayer());
                            if (nearestPlayer != null) {
                                for (String command : commandsOnEnemy) {
                                    if (command != null && !command.isEmpty()) {
                                        decodeCommand(command, nearestPlayer);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() == null || event.getDamager() == null || event.getDamager().getType() != PLAYER) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (player.getItemInHand() != null && player.getItemInHand().hasItemMeta()) {
                String itemName = player.getItemInHand().getItemMeta().getDisplayName();
                if (isItemExistWithName(getItemName(itemName))) {
                    Items item = ItemManager.getItemByName(getItemName(itemName));
                    if (item.getTypesCommandOnEvent() != null) {
                        List<String> typesCommandOnEvent = item.getTypesCommandOnEvent();
                        List<String> commandsOnYou = item.getCommandsOnYou();
                        List<String> commandsOnEnemy = item.getCommandsOnEnemy();

                        for (String type : typesCommandOnEvent) {
                            if (type.equalsIgnoreCase("onhit")) {
                                for (String command : commandsOnYou) {
                                    if (command != null && !command.isEmpty()) {
                                        decodeCommand(command, player);
                                    }
                                }
                                if (event.getEntity() instanceof Player) {
                                    Player target = (Player) event.getEntity();
                                    for (String command : commandsOnEnemy) {
                                        if (command != null && !command.isEmpty()) {
                                            decodeCommand(command, target);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

    private Player getNearestPlayer(Player player){
        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Player p : player.getWorld().getPlayers()) {
            if (p != player) {
                double distance = player.getLocation().distance(p.getLocation());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPlayer = p;
                }
            }
        }
        return nearestPlayer;
    }

    public static void executeCommandWithChance(String command, int chance, Runnable action) {
        if (chance < 0 || chance > 100) {
            throw new IllegalArgumentException("Chance must be between 0 and 100.");
        }

        Random random = new Random();
        int randomValue = random.nextInt(100); // Génère un nombre entre 0 et 99

        if (randomValue < chance) {
            System.out.println("Executing command: " + command);
            action.run(); // Exécute l'action associée à la commande
        } else {
            System.out.println("Command not executed. Chance failed.");
        }
    }

    public static void decodeCommand(String input, Player player) {
        if (input == null || !input.contains(":")) {
            System.out.println("Invalid input format.");
            return;
        }

        String[] parts = input.split(":");
        if (parts.length == 2) {
            String command = parts[0];
            int chance;

            try {
                chance = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid chance value: " + parts[1]);
                return;
            }

            executeCommandWithChance(command, chance, () -> {
                // Action à exécuter si la commande réussit
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()).replace("&", "§"));
            });


        } else {
            System.out.println("Invalid input format.");
        }
    }
}
