package fr.rammex.arkaitems.effects;

import fr.rammex.arkaitems.effects.custom.StealthEffect;

import java.util.HashMap;
import java.util.Map;

public enum CustomsEffects {
    STEALTH("Stealth", "Hides the player's name and makes them invisible", StealthEffect.class);

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