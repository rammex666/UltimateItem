package fr.rammex.arkaitems.items;

import org.bukkit.Material;

import java.util.List;

public class Items {
    private String id;
    private String name;
    private String fullSetName;
    private Material material;
    private int amount;
    private boolean dropable;
    private boolean keepOnDeath;
    private boolean dropPlayerHead;
    private boolean indestructible;
    private List<String> lore;
    private List<String> enchantsOnEquip;
    private List<String> fullSetBonus;
    private List<String> enchantsAssociated;
    private List<String> commandsOnEvent;

    public Items(String id, String name, String fullSetName,Material material, int amount, boolean dropable, boolean keepOnDeath, boolean dropPlayerHead, boolean indestructible, List<String> lore, List<String> enchantsOnEquip, List<String> fullSetBonus, List<String> enchantsAssociated, List<String> commandsOnEvent) {
        this.id = id;
        this.name = name;
        this.fullSetName = fullSetName;
        this.material = material;
        this.amount = amount;
        this.dropable = dropable;
        this.keepOnDeath = keepOnDeath;
        this.dropPlayerHead = dropPlayerHead;
        this.indestructible = indestructible;
        this.lore = lore;
        this.enchantsOnEquip = enchantsOnEquip;
        this.fullSetBonus = fullSetBonus;
        this.enchantsAssociated = enchantsAssociated;
        this.commandsOnEvent = commandsOnEvent;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullSetName() {
        return fullSetName;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isDropable() {
        return dropable;
    }

    public boolean isKeepOnDeath() {
        return keepOnDeath;
    }

    public boolean isDropPlayerHead() {
        return dropPlayerHead;
    }

    public boolean isIndestructible() {
        return indestructible;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<String> getEnchantsOnEquip() {
        return enchantsOnEquip;
    }

    public List<String> getFullSetBonus() {
        return fullSetBonus;
    }

    public List<String> getEnchantsAssociated() {
        return enchantsAssociated;
    }

    public List<String> getCommandsOnEvent() {
        return commandsOnEvent;
    }
}
