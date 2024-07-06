package fr.shoqapik.blacksmithmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.menu.container.SmithCraftContainer;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.Map;

public class BlackSmithRecipe implements Recipe<SmithCraftContainer> {

    private final ResourceLocation resourceLocation;
    final RecipeCategory category;
    final int tier;
    final NonNullList<Ingredient> ingredients;
    final ItemStack result;

    public BlackSmithRecipe(ResourceLocation resourceLocation, RecipeCategory category, int tier, NonNullList<Ingredient> ingredients, ItemStack result) {
        this.resourceLocation = resourceLocation;
        this.category = category;
        this.tier = tier;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public ResourceLocation getId() {
        return this.resourceLocation;
    }

    @Override
    public RecipeType<?> getType() {
        return BlackSmithMod.BLACKSMITH_RECIPE.get();
    }

    public RecipeCategory getCategory() {
        return this.category;
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.SHAPELESS_RECIPE;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(SmithCraftContainer inventory, Level level) {
        StackedContents stackedcontents = new StackedContents();

        for(int j = 0; j < inventory.getContainerSize(); ++j) {
            ItemStack itemstack = inventory.getItem(j);
            if (!itemstack.isEmpty()) {
                stackedcontents.accountStack(itemstack, itemstack.getCount());
            }
        }

        return stackedcontents.canCraft(this, (IntList)null);
    }

    @Override
    public ItemStack assemble(SmithCraftContainer inventory) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= this.ingredients.size();
    }

    public static class Serializer implements RecipeSerializer<BlackSmithRecipe> {
        @Override
        public BlackSmithRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            RecipeCategory category = RecipeCategory.valueOf(json.get("category").getAsString());
            int tier = json.get("tier").getAsInt();
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));

            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for blacksmith recipe");
            } else if (nonnulllist.size() > 6) {
                throw new JsonParseException("Too many ingredients for blacksmith recipe. The maximum is 6.");
            } else {
                ItemStack itemstack = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);
                return new BlackSmithRecipe(recipeId, category, tier, nonnulllist, itemstack);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public BlackSmithRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf pBuffer) {
            RecipeCategory category = RecipeCategory.valueOf(pBuffer.readUtf());
            int tier = pBuffer.readInt();

            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack itemstack = pBuffer.readItem();
            return new BlackSmithRecipe(recipeId, category, tier, nonnulllist, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BlackSmithRecipe recipe) {
            pBuffer.writeUtf(recipe.getCategory().name());
            pBuffer.writeInt(recipe.getTier());
            pBuffer.writeVarInt(recipe.ingredients.size());

            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(recipe.result);
        }
    }

/*

    public Map<String, Integer> getRequiredItems() {
        return requiredItems;
    }
    public String getCraftedItem() {
        return craftedItem;
    }


    public ItemStack getCraftedItemStack() {
        if(itemStack != null) return itemStack;
        itemStack = getItemStack(craftedItem);
        return itemStack;
    }

    public ItemStack getItemStack(Ingredient ingredient) {
        return ingredient.get
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    public boolean hasItems(Player player){
        for(Map.Entry<String, Integer> requieredItem: getRequiredItems().entrySet()){
            if(player.getInventory().countItem(getItemStack(requieredItem.getKey()).getItem()) < requieredItem.getValue()){
                return false;
            }
        }
        return true;
    }

    public boolean hasItems(AbstractContainerMenu containerMenu){
        for(Map.Entry<String, Integer> requieredItem: getRequiredItems().entrySet()){
            if(countItem(containerMenu, getItemStack(requieredItem.getKey()).getItem()) < requieredItem.getValue()){
                return false;
            }
        }
        return true;
    }

    private int countItem(AbstractContainerMenu containerMenu, Item item) {
        int amount = 0;

        for(ItemStack itemStack : containerMenu.getItems()) {
            if (itemStack.getItem().equals(item)) {
                amount += itemStack.getCount();
            }
        }

        return amount;
    }*/
}
