package fr.rammex.arkaitems;

import fr.rammex.arkaitems.commands.ItemCommand;
import fr.rammex.arkaitems.commands.ItemCraftCommand;
import fr.rammex.arkaitems.craft.CraftManager;
import fr.rammex.arkaitems.craft.CraftSetup;
import fr.rammex.arkaitems.database.SQLiteManager;
import fr.rammex.arkaitems.effects.custom.*;
import fr.rammex.arkaitems.events.CustomEffectsListener;
import fr.rammex.arkaitems.events.FullSetListener;
import fr.rammex.arkaitems.events.GUIListener;
import fr.rammex.arkaitems.events.ItemListener;
import fr.rammex.arkaitems.events.autoplaceblock.AutoPlaceBlockListener;
import fr.rammex.arkaitems.events.commandsonevent.CommandsOnEventListener;
import fr.rammex.arkaitems.events.pickaxemultiblock.PickaxeMultiBlockListener;
import fr.rammex.arkaitems.events.sellstick.SellStickListener;
import fr.rammex.arkaitems.fullsets.FullSetManager;
import fr.rammex.arkaitems.fullsets.FullSetSetup;
import fr.rammex.arkaitems.gui.CustomCraftGUI;
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

        // ITEMS
        loadItems();

        // CRAFT
        CraftSetup.setupCrafts();

        // FULLSETS
        FullSetSetup.setupFullSets();

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
        getServer().getPluginManager().registerEvents(new BoltEffect(), this);
        getServer().getPluginManager().registerEvents(new SpawnerRemoverEffect(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new CommandsOnEventListener(), this);
        getServer().getPluginManager().registerEvents(new FullSetListener(), this);
        getServer().getPluginManager().registerEvents(new CutTreeEffect(), this);
        getServer().getPluginManager().registerEvents(new LifeStealEffect(), this);
        getServer().getPluginManager().registerEvents(new NoFallEffect(), this);
        getServer().getPluginManager().registerEvents(new CustomCraftGUI(), this);
    }

    private void loadCommands(){
        getCommand("arkaitems").setExecutor(new ItemCommand());
        getCommand("itemcraft").setExecutor(new ItemCraftCommand());
    }

    private void getLoadMessages(){
        getLogger().info("Plugin loaded !");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info(FullSetManager.getSetCount()+ " fullsets loaded.");
        getLogger().info(ItemManager.getItemCount() + " items loaded.");
        getLogger().info("Sellsticks loaded: " + SellStickManager.getSellStickCount());
        getLogger().info("AutoPlaceBlocks loaded: " + AutoPlaceBlockManager.getItemCount());
        getLogger().info("PickaxeMultiBlocks loaded: " + PickaxeMultiBlockManager.getItemCount());
        getLogger().info("Crafts loaded: " + CraftManager.getCraftCount());
    }

    private void loadItems(){
        ItemSetup.setupItems();
        SellStickSetup.setupItems();
        AutoPlaceBlockSetup.setupItems();
        PickaxeMultiBlockSetup.setupItems();
    }
}
