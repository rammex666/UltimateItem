package fr.rammex.arkaitems.craft;

import java.util.List;

public class Craft {
    private String id;
    private String itemId;
    private int amount;
    private List<String> recipe;
    private List<String> require;

    public Craft(String id, String itemId, int amount, List<String> recipe, List<String> require) {
        this.id = id;
        this.itemId = itemId;
        this.amount = amount;
        this.recipe = recipe;
        this.require = require;
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
}
