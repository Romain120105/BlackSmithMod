package fr.shoqapik.btemobs.client.widget;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;

import java.util.Iterator;
import java.util.List;

public class BteRecipeBookComponent extends RecipeBookComponent {

    @Override
    public void setupGhostRecipe(Recipe<?> pRecipe, List<Slot> pSlots) {
        ItemStack itemstack = pRecipe.getResultItem();
        this.ghostRecipe.setRecipe(pRecipe);
        if(pRecipe instanceof UpgradeRecipe) {
            UpgradeRecipe recipe = (UpgradeRecipe) pRecipe;
            this.ghostRecipe.addIngredient(recipe.base, (pSlots.get(0)).x, (pSlots.get(0)).y);
            this.ghostRecipe.addIngredient(recipe.addition, (pSlots.get(1)).x, (pSlots.get(1)).y);
        }
        //this.ghostRecipe.addIngredient(Ingredient.of(itemstack), (pSlots.get(0)).x, (pSlots.get(0)).y);
        this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), pRecipe, pRecipe.getIngredients().iterator(), 0);
    }
}
