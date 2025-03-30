package fr.rammex.arkaitems.commands;

import fr.rammex.arkaitems.ArkaItems;
import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.ItemSetup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(args.length == 0){
            sender.sendMessage("§c/arkaitem reload");
            return false;
        }

        switch (args[0]){
            case "reload":
                reload();
                sender.sendMessage("§aConfig reloaded");
                break;
            case "give":
                String target = args[1];
                Player player = ArkaItems.instance.getServer().getPlayer(target);
                if(player == null){
                    sender.sendMessage("§cPlayer not found");
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
            default:
                break;
        }


        return false;
    }

    private void reload(){
        ArkaItems.instance.reloadConfig();
        ItemSetup.setupItems();
    }

    private void give(String itemName ,Player player, int amount){
        if(!ItemManager.isItemExist(itemName)){
            player.sendMessage("§cItem not found");
        } else {
            player.getInventory().addItem(ItemManager.createItem(player, itemName, amount));
        }
    }
}
