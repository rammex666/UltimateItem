package fr.rammex.arkaitems;

import fr.rammex.arkaitems.commands.ItemCommand;
import fr.rammex.arkaitems.database.SQLiteManager;
import fr.rammex.arkaitems.events.CustomEffectsListener;
import fr.rammex.arkaitems.events.GUIListener;
import fr.rammex.arkaitems.events.autoplaceblock.AutoPlaceBlockListener;
import fr.rammex.arkaitems.events.pickaxemultiblock.PickaxeMultiBlockListener;
import fr.rammex.arkaitems.events.sellstick.SellStickListener;
import fr.rammex.arkaitems.items.ItemManager;
import fr.rammex.arkaitems.items.ItemSetup;
import fr.rammex.arkaitems.items.specialitems.autoplaceblock.AutoPlaceBlockManager;
import fr.rammex.arkaitems.items.specialitems.autoplaceblock.AutoPlaceBlockSetup;
import fr.rammex.arkaitems.items.specialitems.pickaxemultiblock.PickaxeMultiBlockManager;
import fr.rammex.arkaitems.items.specialitems.pickaxemultiblock.PickaxeMultiBlockSetup;
import fr.rammex.arkaitems.items.specialitems.sellstick.SellStickManager;
import fr.rammex.arkaitems.items.specialitems.sellstick.SellStickSetup;
import fr.rammex.arkaitems.utils.YamlFiles;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class ArkaItems extends JavaPlugin {

    public static ArkaItems instance;

    @Override
    public void onEnable() {
        instance = this;

        // YAML FILES
        saveDefaultConfig();
        YamlFiles.loadFiles();

        // EVENTS
        loadEvents();

        // COMMANDS
        loadCommands();

        // DATABASE
        SQLiteManager sqLiteManager = new SQLiteManager("arkaitem", new File(getDataFolder(), "data.db"));
        sqLiteManager.load();

        //ITEMS
        loadItems();

        // LOAD MESSAGES
        getLoadMessages();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadEvents(){
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new SellStickListener(), this);
        getServer().getPluginManager().registerEvents(new AutoPlaceBlockListener(), this);
        getServer().getPluginManager().registerEvents(new PickaxeMultiBlockListener(), this);
        getServer().getPluginManager().registerEvents(new CustomEffectsListener(), this);
    }

    private void loadCommands(){
        getCommand("arkaitems").setExecutor(new ItemCommand());
    }

    private void getLoadMessages(){
        getLogger().info("Plugin loaded !");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info(ItemManager.getItemCount() + " items loaded.");
        getLogger().info("Sellsticks loaded: " + SellStickManager.getSellStickCount());
        getLogger().info("AutoPlaceBlocks loaded: " + AutoPlaceBlockManager.getItemCount());
        getLogger().info("PickaxeMultiBlocks loaded: " + PickaxeMultiBlockManager.getItemCount());
    }

    private void loadItems(){
        ItemSetup.setupItems();
        SellStickSetup.setupItems();
        AutoPlaceBlockSetup.setupItems();
        PickaxeMultiBlockSetup.setupItems();
    }
}
