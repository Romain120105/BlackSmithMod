package fr.shoqapik.blacksmithmod.menu;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.menu.container.SmithCraftContainer;
import fr.shoqapik.blacksmithmod.menu.slot.CraftInputSlot;
import fr.shoqapik.blacksmithmod.recipe.BlackSmithRecipe;
import fr.shoqapik.blacksmithmod.recipe.RecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class SmithCraftMenu extends AbstractContainerMenu {

    private final SmithCraftContainer craftSlots = new SmithCraftContainer(this, 3, 3);


    public SmithCraftMenu(int p_39356_, Inventory p_39357_) {
        super(BlackSmithMod.SMITH_CRAFT_MENU.get(), p_39356_);

        this.addSlot(new CraftInputSlot(this.craftSlots, 0, 233,9));
        this.addSlot(new CraftInputSlot(this.craftSlots, 1, 254,9));
        this.addSlot(new CraftInputSlot(this.craftSlots, 2, 212,29));
        this.addSlot(new CraftInputSlot(this.craftSlots, 3, 233,29));
        this.addSlot(new CraftInputSlot(this.craftSlots, 4, 254,29));
        this.addSlot(new CraftInputSlot(this.craftSlots, 5, 233,50));


        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(p_39357_, i1 + k * 9 + 9, 161 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(p_39357_, l, 161 + l * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public void placeRecipe(Player player, String item) {
        BlackSmithRecipe recipe = RecipeManager.getRecipe(item);

        for(int i = 0; i < 6; i++){
            Slot slot = getSlot(i);
            if(slot.hasItem()){
                player.getInventory().add(slot.getItem());
            }
        }
        if(recipe != null){
            if(recipe.hasItems(player)){
                int index = 0;
                for(Map.Entry<String, Integer> requieredItem: recipe.getRequiredItems().entrySet()){
                    Item minecraftItem = recipe.getItemStack(requieredItem.getKey()).getItem();
                    int removed = 0;
                    for(ItemStack stack : player.getInventory().items){
                        if(stack.getItem() == minecraftItem){
                            if(removed < requieredItem.getValue()) {
                                int toRemove = requieredItem.getValue() - removed;
                                if (toRemove > stack.getCount()) toRemove = stack.getCount();
                                stack.shrink(toRemove);
                                removed += toRemove;
                            }
                        }
                    }
                    this.getSlot(index).set(new ItemStack(minecraftItem, requieredItem.getValue()));
                    index +=1;

                }
            }
        }
    }
}
