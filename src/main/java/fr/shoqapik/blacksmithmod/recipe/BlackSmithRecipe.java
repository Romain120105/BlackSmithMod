package fr.shoqapik.blacksmithmod.recipe;

import java.util.Map;

public class BlackSmithRecipe {

    private int tier;
    private Map<String, Integer> requiredItems;
    private String craftedItem;

    public int getTier() {
        return tier;
    }

    public Map<String, Integer> getRequiredItems() {
        return requiredItems;
    }

    public String getCraftedItem() {
        return craftedItem;
    }
}
