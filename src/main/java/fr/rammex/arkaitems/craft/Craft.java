package fr.rammex.arkaitems.craft;

import java.util.List;

public class Craft {
    private String id;
    private String itemId;
    private int amount;
    private List<String> recipe;
    private List<String> require;
    private List<String> commandsSuccess;

    public Craft(String id, String itemId, int amount, List<String> recipe, List<String> require, List<String> commandsSuccess) {
        this.id = id;
        this.itemId = itemId;
        this.amount = amount;
        this.recipe = recipe;
        this.require = require;
        this.commandsSuccess = commandsSuccess;
    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getRecipe() {
        return recipe;
    }

    public List<String> getRequire() {
        return require;
    }

    public List<String> getCommandsSuccess() {
        return commandsSuccess;
    }
}
