package fr.shoqapik.blacksmithmod.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class BlackSmithRecipe {

    private RecipeCategory category= RecipeCategory.ALL;
    private int tier = 0;
    private Map<String, Integer> requiredItems = new HashMap<>();
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

    public BlackSmithRecipe(String craftedItem) {
        this.craftedItem = craftedItem;
    }

    public ItemStack getCraftedItemStack() {
        ResourceLocation key = new ResourceLocation(craftedItem);
        if(ForgeRegistries.ITEMS.containsKey(key)){
            return new ItemStack(ForgeRegistries.ITEMS.getDelegate(key).get().get());
        }
        return new ItemStack(Blocks.BARRIER);
    }
}
