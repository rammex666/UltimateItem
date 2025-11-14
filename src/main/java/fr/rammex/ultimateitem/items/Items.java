package fr.rammex.ultimateitem.items;

import fr.rammex.ultimateitem.effects.CustomsEffects;
import org.bukkit.Material;

import java.util.List;

public class Items {
    private String id;
    private String name;
    private Material material;
    private CustomsEffects customsEffects;
    private int amount;
    private int durability;
    private boolean dropable;
    private boolean keepOnDeath;
    private boolean dropPlayerHead;
    private boolean indestructible;
    private List<String> lore;
    private List<String> enchantsOnEquip;
    private List<String> enchantsAssociated;
    private List<String> typesCommandOnEvent;
    private List<String> commandsOnYou;
    private List<String> commandsOnEnemy;


    public Items(String id, String name, Material material, CustomsEffects customsEffects, int amount, int durability, boolean dropable, boolean keepOnDeath, boolean dropPlayerHead, boolean indestructible, List<String> lore, List<String> enchantsOnEquip, List<String> enchantsAssociated, List<String> typesCommandOnEvent, List<String> commandsOnYou, List<String> commandsOnEnemy) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.customsEffects = customsEffects;
        this.amount = amount;
        this.durability = durability;
        this.dropable = dropable;
        this.keepOnDeath = keepOnDeath;
        this.dropPlayerHead = dropPlayerHead;
        this.indestructible = indestructible;
        this.lore = lore;
        this.enchantsOnEquip = enchantsOnEquip;
        this.enchantsAssociated = enchantsAssociated;
        this.typesCommandOnEvent = typesCommandOnEvent;
        this.commandsOnYou = commandsOnYou;
        this.commandsOnEnemy = commandsOnEnemy;
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
    public CustomsEffects getCustomsEffects() {
        return customsEffects;
    }
    public int getAmount() {
        return amount;
    }
    public int getDurability() {
        return durability;
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
    public List<String> getEnchantsAssociated() {
        return enchantsAssociated;
    }
    public List<String> getTypesCommandOnEvent() {
        return typesCommandOnEvent;
    }
    public List<String> getCommandsOnYou() {
        return commandsOnYou;
    }
    public List<String> getCommandsOnEnemy() {
        return commandsOnEnemy;
    }
}
