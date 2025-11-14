package fr.rammex.ultimateitem;


import fr.rammex.ultimateitem.commands.ItemCommand;
import fr.rammex.ultimateitem.commands.ItemCraftCommand;
import fr.rammex.ultimateitem.craft.CraftManager;
import fr.rammex.ultimateitem.craft.CraftSetup;
import fr.rammex.ultimateitem.database.SQLiteManager;
import fr.rammex.ultimateitem.effects.custom.*;
import fr.rammex.ultimateitem.events.CustomEffectsListener;
import fr.rammex.ultimateitem.events.FullSetListener;
import fr.rammex.ultimateitem.events.GUIListener;
import fr.rammex.ultimateitem.events.ItemListener;
import fr.rammex.ultimateitem.events.autoplaceblock.AutoPlaceBlockListener;
import fr.rammex.ultimateitem.events.commandsonevent.CommandsOnEventListener;
import fr.rammex.ultimateitem.events.pickaxemultiblock.PickaxeMultiBlockListener;
import fr.rammex.ultimateitem.events.sellstick.SellStickListener;
import fr.rammex.ultimateitem.fullsets.FullSetManager;
import fr.rammex.ultimateitem.fullsets.FullSetSetup;
import fr.rammex.ultimateitem.gui.CustomCraftGUI;
import fr.rammex.ultimateitem.items.ItemManager;
import fr.rammex.ultimateitem.items.ItemSetup;
import fr.rammex.ultimateitem.items.specialitems.autoplaceblock.AutoPlaceBlockManager;
import fr.rammex.ultimateitem.items.specialitems.autoplaceblock.AutoPlaceBlockSetup;
import fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock.PickaxeMultiBlockManager;
import fr.rammex.ultimateitem.items.specialitems.pickaxemultiblock.PickaxeMultiBlockSetup;
import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStickManager;
import fr.rammex.ultimateitem.items.specialitems.sellstick.SellStickSetup;
import fr.rammex.ultimateitem.utils.YamlFiles;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class UltimateItem extends JavaPlugin {

    public static UltimateItem instance;

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
        SQLiteManager sqLiteManager = new SQLiteManager("ultimateitem", new File(getDataFolder(), "data.db"));
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
        getServer().getPluginManager().registerEvents(new ChestReaderEffect(), this);
        getServer().getPluginManager().registerEvents(new MaskFarmEffect(), this);
        getServer().getPluginManager().registerEvents(new TeleportEffect(), this);
        getServer().getPluginManager().registerEvents(new TpToPlayerEffect(), this);
        getServer().getPluginManager().registerEvents(new MoneyStealEffect(), this);
    }

    private void loadCommands(){
        getCommand("ultimateitem").setExecutor(new ItemCommand());
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
