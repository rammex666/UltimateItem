package fr.rammex.ultimateitem.fullsets;

import java.util.List;


public class FullSet {
    private String id;
    private String name;
    private List<String> setItems;
    private List<String> fullSetEffects;

    public FullSet(String id, String name, List<String> setItems, List<String> fullSetEffects) {
        this.id = id;
        this.name = name;
        this.setItems = setItems;
        this.fullSetEffects = fullSetEffects;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getSetItems() {
        return setItems;
    }

    public List<String> getFullSetEffects() {
        return fullSetEffects;
    }
}
