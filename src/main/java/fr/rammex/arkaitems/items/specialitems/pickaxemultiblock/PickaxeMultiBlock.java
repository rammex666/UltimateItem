package fr.rammex.arkaitems.items.specialitems.pickaxemultiblock;

import org.bukkit.Material;

import java.util.List;

public class PickaxeMultiBlock {
    private String id;
    private String name;
    private Material material;
    private int durability;
    private boolean dropable;
    private boolean keepOnDeath;
    private boolean indestructible;
    private List<String> lore;
    private List<Integer> miningArea;

    public PickaxeMultiBlock(String id, String name, Material material, int durability, boolean dropable, boolean keepOnDeath, boolean indestructible, List<String> lore, List<Integer> miningArea) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.dropable = dropable;
        this.keepOnDeath = keepOnDeath;
        if(indestructible==true){
            this.durability = -1;
        } else {
            this.durability = durability;
        }
        this.indestructible = indestructible;
        this.lore = lore;
        this.miningArea = miningArea;
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
    public List<Integer> getMiningArea() {
        return miningArea;
    }
}
