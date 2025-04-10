package fr.rammex.arkaitems.items.specialitems.autoplaceblock;

import org.bukkit.Material;

import java.util.List;

public class AutoPlaceBlock {
    private String id;
    private String name;
    private String direction;
    private Material material;
    private Material blockTypeFromInventory;
    private int durability;
    private int from;
    private int to;
    private boolean dropable;
    private boolean keepOnDeath;
    private boolean getItemFromInventory;
    private boolean indestructible;
    private List<String> lore;

    public AutoPlaceBlock(String id, String name, String direction, Material material, Material blockTypeFromInventory, int durability, int from, int to, boolean dropable, boolean keepOnDeath, boolean getItemFromInventory, boolean indestructible, List<String> lore) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.material = material;
        this.from = from;
        this.to = to;
        this.dropable = dropable;
        this.keepOnDeath = keepOnDeath;
        this.getItemFromInventory = getItemFromInventory;
        this.blockTypeFromInventory = blockTypeFromInventory;
        if(indestructible==true){
            this.durability = -1;
        } else {
            this.durability = durability;
        }
        this.indestructible = indestructible;
        this.lore = lore;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public Material getMaterial() {
        return material;
    }

    public Material getBlockTypeFromInventory() {
        return blockTypeFromInventory;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isDropable() {
        return dropable;
    }

    public boolean isKeepOnDeath() {
        return keepOnDeath;
    }

    public boolean isGetItemFromInventory() {
        return getItemFromInventory;
    }

    public boolean isIndestructible() {
        return indestructible;
    }

    public int getDurability() {
        return durability;
    }

    public List<String> getLore() {
        return lore;
    }
}
