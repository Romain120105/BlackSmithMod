package fr.shoqapik.blacksmithmod.recipe;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
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
            BlackSmithMod.LOGGER.error("Unknown item '{}' found in recipe for item '{}'.", key, craftedItem);
            return new ItemStack(Blocks.STONE);
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
    }
}
