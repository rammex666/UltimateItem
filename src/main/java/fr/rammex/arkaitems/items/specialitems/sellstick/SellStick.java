package fr.rammex.arkaitems.items.specialitems.sellstick;

import org.bukkit.Material;

import java.util.List;

public class SellStick {
    private String id;
    private String name;
    private Material material;
    private int durability;
    private float sellMultiplier;
    private boolean dropable;
    private boolean keepOnDeath;
    private boolean indestructible;
    private List<String> lore;

    public SellStick(String id, String name, Material material, float sellMultiplier, int durability, boolean dropable, boolean keepOnDeath,boolean indestructible, List<String> lore) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.sellMultiplier = sellMultiplier;
        this.dropable = dropable;
        this.keepOnDeath = keepOnDeath;
        if(indestructible==true){
            this.durability = 0;
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

    public Material getMaterial() {
        return material;
    }

    public float getSellMultiplier() {
        return sellMultiplier;
    }

    public boolean isDropable() {
        return dropable;
    }

    public boolean isKeepOnDeath() {
        return keepOnDeath;
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
