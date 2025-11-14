package fr.rammex.ultimateitem.commands;

import fr.rammex.ultimateitem.gui.CustomCraftGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCraftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if(args.length == 0){
            player.sendMessage("§cPlease specify the size of the item you want to craft.");
            return false;
        }



        CustomCraftGUI customCraftGUI = new CustomCraftGUI();
        customCraftGUI.buildInventory(player, Integer.parseInt(args[0]));









        return false;
    }
}
