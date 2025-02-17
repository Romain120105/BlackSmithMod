
package fr.shoqapik.btemobs.recipe;

import com.google.gson.JsonObject;
import fr.shoqapik.btemobs.menu.container.BlacksmithCraftContainer;
import fr.shoqapik.btemobs.recipe.api.BteAbstractRecipe;
import fr.shoqapik.btemobs.recipe.api.RecipeCategory;
import fr.shoqapik.btemobs.registry.BteMobsRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class BlacksmithUpgradeRecipe extends BteAbstractRecipe {

    public BlacksmithUpgradeRecipe(ResourceLocation resourceLocation, RecipeCategory category, int tier, Ingredient base, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(resourceLocation, category, tier, ingredients, result);
        ingredients.add(0, base);
    }

    @Override
    public ItemStack assemble(BlacksmithCraftContainer pInv) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = pInv.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return new BlacksmithUpgradeRecipe.Serializer();
    }

    @Override
    public RecipeType<?> getType() {
        return BteMobsRecipeTypes.BLACKSMITH_UPGRADE_RECIPE.get();
    }

    public static class Serializer extends AbstractSerializer<BlacksmithUpgradeRecipe> {
        @Override
        public BlacksmithUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            return fromJson(recipeId, json, base);
        }

        @Override
        public BlacksmithUpgradeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf pBuffer) {
            Ingredient base = Ingredient.fromNetwork(pBuffer);
            return fromNetwork(recipeId, pBuffer, base);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BlacksmithUpgradeRecipe recipe) {
            recipe.ingredients.get(0).toNetwork(pBuffer);
            recipe.ingredients.remove(0);
            super.toNetwork(pBuffer, recipe);
        }

        @Override
        protected BlacksmithUpgradeRecipe of(ResourceLocation resourceLocation, RecipeCategory category, int tier, NonNullList<Ingredient> ingredients, ItemStack result, Object... objects) {
            return new BlacksmithUpgradeRecipe(resourceLocation, category, tier, (Ingredient)objects[0], ingredients, result);
        }
    }
}
