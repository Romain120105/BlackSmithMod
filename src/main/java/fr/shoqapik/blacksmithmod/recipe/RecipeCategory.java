package fr.shoqapik.blacksmithmod.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum RecipeCategory {
    ALL(new ItemStack(Items.CRAFTING_TABLE)),
    WEAPONS(new ItemStack(Items.NETHERITE_SWORD)),
    ARMORS(new ItemStack(Items.NETHERITE_CHESTPLATE)),
    OTHERS(new ItemStack(Items.STICK));

    public ItemStack item;

    RecipeCategory(ItemStack stack){
        this.item = stack;
    }
}
