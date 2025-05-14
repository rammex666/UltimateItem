package fr.rammex.arkaitems.effects;

import fr.rammex.arkaitems.effects.custom.*;

import java.util.HashMap;
import java.util.Map;

public enum CustomsEffects {
    STEALTH("Stealth", "Hides the player's name and makes them invisible", StealthEffect.class),
    BOLT("Bolt", "Strikes lightning at the target location", BoltEffect.class),
    SPAWNERREMOVER("SpawnerRemover", "Removes the spawner", SpawnerRemoverEffect.class),
    CUTTREE("CutTree", "Cuts the tree", CutTreeEffect.class),
    LIFESTEAL("LifeSteal", "Steals life from the target", LifeStealEffect.class),
    NOFALL("NoFall", "Prevents fall damage", NoFallEffect.class),
    MONEYSTEAL("MoneySteal", "Steals money from the target", MoneyStealEffect.class),
    TELEPORT("Teleport", "Chance de se régénérer et d’être téléporté dans un rayon défini lors de la mort.", TeleportEffect.class),
    TPTOPLAYER("TeleportToPlayer", "Teleport to the player", TpToPlayerEffect.class),
    CHESTREADER("ChestReader", "Reads the chest", ChestReaderEffect.class),
    MASKFARM("MaskFarm", "Farms the mask", MaskFarmEffect.class),
    REPAIRTOOL("RepairTool", "Repairs the tool", RepairToolEffect.class),
    ARMORDODGE("ArmorDodge", "Dodges the armor", ArmorDodgeEffect.class),
    ROTATEENEMY("RotateEnemy", "Rotates the enemy", RotateEnemyEffect.class),
    RANDOMITEMINVENTORY("RandomItemInventory", "Gives a random item in the inventory", RandomItemInventoryEffect.class);

    private final String name;
    private final String description;
    private final Class<?> effectClass;

    private static final Map<String, CustomsEffects> NAME_MAP = new HashMap<>();

    static {
        for (CustomsEffects effect : values()) {
            NAME_MAP.put(effect.getName().toLowerCase(), effect);
        }
    }

    CustomsEffects(String name, String description, Class<?> effectClass) {
        this.name = name;
        this.description = description;
        this.effectClass = effectClass;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getEffectClass() {
        return effectClass;
    }

    public static CustomsEffects match(String name) {
        return NAME_MAP.get(name.toLowerCase());
    }
}