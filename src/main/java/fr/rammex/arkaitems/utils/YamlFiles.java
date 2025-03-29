package fr.rammex.arkaitems.utils;

import fr.rammex.arkaitems.ArkaItems;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlFiles {

    private static FileConfiguration messagesConf;
    private static FileConfiguration itemsConf;
    private static FileConfiguration craftsConf;
    private static File file;

    private static void loadFile(String fileName, String folder) {
        if(folder == null){
            file = new File(ArkaItems.instance.getDataFolder(), fileName + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                ArkaItems.instance.saveResource(fileName + ".yml", false);
            }
        } else {
            file = new File(ArkaItems.instance.getDataFolder() + "/" + folder, fileName + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                ArkaItems.instance.saveResource(folder+"/"+fileName + ".yml", false);
            }
        }

        FileConfiguration fileConf = new YamlConfiguration();
        try {
            fileConf.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        switch (fileName) {
            case "messages":
                messagesConf = fileConf;
                break;
            case "items":
                itemsConf = fileConf;
                break;
            case "crafts":
                craftsConf = fileConf;
                break;
        }
    }

    public static FileConfiguration getMessagesConf() {
        return messagesConf;
    }

    public static FileConfiguration getItemsConf() {
        return itemsConf;
    }

    public static FileConfiguration getCraftsConf() {
        return craftsConf;
    }



    public static void loadFiles() {
        loadFile("messages", null);
        loadFile("items", null);
        loadFile("crafts", null);
    }
}
