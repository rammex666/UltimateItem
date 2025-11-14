package fr.rammex.ultimateitem.commands;

import fr.rammex.ultimateitem.UltimateItem;
import fr.rammex.ultimateitem.gui.ItemsGUI;
import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.ItemSetup;
import fr.rammex.ultimateitem.items.specialitems.autoplaceblock.AutoPlaceBlockSetup;
import fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock.PickaxeMultiBlockSetup;
import fr.rammex.ultimateitem.utils.ItemMetadata;
import fr.rammex.ultimateitem.utils.YamlFiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(args.length == 0){
            sender.sendMessage("§c/arkaitem <reload/give/menu>");
            return false;
        }

        switch (args[0]){
            case "reload":
                if(!sender.hasPermission("ultimateitem.admin")){
                    sender.sendMessage("§cYou don't have permission to use this command");
                    return false;
                }
                reload();
                sender.sendMessage("§aConfig reloaded");
                break;
            case "getid":
                getId((Player) sender);
                break;
            case "give":
                String target = args[1];
                Player player = UltimateItem.instance.getServer().getPlayer(target);
                if(player == null){
                    sender.sendMessage("§cPlayer not found");
                    return false;
                }
                if(!sender.hasPermission("ultimateitem.admin")){
                    sender.sendMessage("§cYou don't have permission to use this command");
                    return false;
                }
                String itemName = args[2];
                if(args.length == 3){
                    give(itemName, player, 1);
                    return true;
                } else {
                    int amount = Integer.parseInt(args[3]);
                    give(itemName, player, amount);
                    return true;
                }
            case "menu":
                if(!(sender instanceof Player)){
                    sender.sendMessage("§cYou must be a player to use this command");
                    return false;
                }
                if(!sender.hasPermission("ultimateitem.menu")){
                    sender.sendMessage("§cYou don't have permission to use this command");
                    return false;
                }
                Player p = (Player) sender;
                ItemsGUI.buildInventory(p);
                break;
            default:
                break;
        }


        return false;
    }

    private void reload(){
        YamlFiles.loadFiles();
        UltimateItem.instance.reloadConfig();
        AutoPlaceBlockSetup.setupItems();
        PickaxeMultiBlockSetup.setupItems();
        ItemSetup.setupItems();
    }

    private void give(String itemName, Player player, int amount) {
        if (!ItemManager.isItemExist(itemName)) {
            player.sendMessage("§cItem not found");
        } else {
            ItemStack item = ItemManager.createItem(player, itemName, amount);
            if (item == null) {
                player.sendMessage("§cItem not found");
            } else {
                player.getInventory().addItem(item);

            }
        }
    }

    private void getId(Player player){
        if(!ItemMetadata.hasMetadata(player.getInventory().getItemInHand(), "ID")){
            player.sendMessage("§cItem not found");
        } else {
            player.sendMessage("§aYour Item ID is: " + ItemMetadata.getMetadata(player.getInventory().getItemInHand(), "ID"));
        }
    }
}
