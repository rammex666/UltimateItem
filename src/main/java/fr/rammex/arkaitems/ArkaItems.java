package fr.rammex.arkaitems;

import fr.rammex.arkaitems.commands.ItemCommand;
import fr.rammex.arkaitems.database.SQLiteManager;
import fr.rammex.arkaitems.events.ItemListener;
import fr.rammex.arkaitems.items.ItemSetup;
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
        ItemSetup.setupItems();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadEvents(){
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    private void loadCommands(){
        getCommand("arkaitems").setExecutor(new ItemCommand());
    }
}
