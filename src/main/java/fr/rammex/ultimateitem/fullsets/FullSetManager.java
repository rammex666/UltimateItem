package fr.rammex.ultimateitem.fullsets;

import fr.rammex.ultimateitem.items.Items;

import java.util.HashMap;
import java.util.Map;

public class FullSetManager {

    private static Map<String, FullSet> fullSetMap = new HashMap<>();

    public static void addSet(FullSet set) {
        fullSetMap.put(set.getId(), set);
    }

    public static Map<String, FullSet> getItems() {
        return fullSetMap;
    }

    public static FullSet getSetById(String id) {
        return fullSetMap.get(id);
    }

    public static FullSet getSetByName(String name) {
        for (FullSet set : fullSetMap.values()) {
            if (set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }
        return null;
    }

    public static boolean isSetExist(String id) {
        return fullSetMap.containsKey(id);
    }

    public static void resetSets() {
        fullSetMap.clear();
    }

    public static int getSetCount() {
        return fullSetMap.size();
    }

    public static boolean isFullSet(Items item) {
        for (FullSet set : fullSetMap.values()) {
            if (set.getSetItems().contains(item.getId())) {
                return true;
            }
        }
        return false;
    }

    public static FullSet getFullSet(Items item) {
        for (FullSet set : fullSetMap.values()) {
            if (set.getSetItems().contains(item.getId())) {
                return set;
            }
        }
        return null;
    }
}
