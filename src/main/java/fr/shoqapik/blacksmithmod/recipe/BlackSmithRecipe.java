package fr.shoqapik.blacksmithmod.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
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

    private transient ItemStack itemStack;

    public BlackSmithRecipe(String craftedItem) {
        this.craftedItem = craftedItem;
    }

    public int getTier() {
        return tier;
    }
    public Map<String, Integer> getRequiredItems() {
        return requiredItems;
    }
    public String getCraftedItem() {
        return craftedItem;
    }
    public RecipeCategory getCategory() {
        return category;
    }

    public ItemStack getCraftedItemStack() {
        if(itemStack != null) return itemStack;
        itemStack = getItemStack(craftedItem);
        return itemStack;
    }

    public ItemStack getItemStack(String key){
        ResourceLocation location = new ResourceLocation(key);
        if(ForgeRegistries.ITEMS.containsKey(location)){
            return new ItemStack(ForgeRegistries.ITEMS.getDelegate(location).get().get());
        }else{
            return new ItemStack(Blocks.BARRIER);
        }
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
}
