package fr.shoqapik.btemobs.recipe.api;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum RecipeCategory {
    ALL(new ItemStack(Items.CRAFTING_TABLE)),
    WEAPONS(new ItemStack(Items.NETHERITE_SWORD)),
    ARMORS(new ItemStack(Items.NETHERITE_CHESTPLATE)),
    OTHERS(new ItemStack(Items.STICK));

    public final ItemStack item;

    RecipeCategory(ItemStack stack){
        this.item = stack;
    }

    public RecipeBookCategories getVanillaCategory() {
        return RecipeBookCategories.valueOf("BLACKSMITH_" + this.name());
    }
}
