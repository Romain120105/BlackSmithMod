package fr.shoqapik.blacksmithmod.menu;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.menu.container.SmithCraftContainer;
import fr.shoqapik.blacksmithmod.menu.slot.CraftInputSlot;
import fr.shoqapik.blacksmithmod.packets.CraftItemPacket;
import fr.shoqapik.blacksmithmod.packets.PlaceRecipePacket;
import fr.shoqapik.blacksmithmod.recipe.BlackSmithRecipe;
import fr.shoqapik.blacksmithmod.recipe.RecipeManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class SmithCraftMenu extends AbstractContainerMenu {

    private final SmithCraftContainer craftSlots = new SmithCraftContainer(this, 3, 3);
    public BlackSmithRecipe selectedRecipe = null;

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

    /*@Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }*/

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.clearContainer(pPlayer, this.craftSlots);
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

    public void craftItemClient() {
        if(selectedRecipe == null) return;
        BlackSmithMod.sendToServer(new CraftItemPacket(selectedRecipe.getCraftedItem()));
        selectedRecipe = null;
    }

    public void craftItemServer(ServerPlayer serverPlayer, String craftedItem) {
        BlackSmithRecipe recipe = RecipeManager.getRecipe(craftedItem);
        if(recipe != null && recipe.hasItems(this)) {
            //this.craftSlots.clearContent();
            for(int i = 0; i < this.craftSlots.getContainerSize(); ++i) {
                Inventory inventory = serverPlayer.getInventory();
                if (inventory.player instanceof ServerPlayer) {
                    this.craftSlots.removeItem(i, this.craftSlots.getItem(i).getCount());
                }
            }

            ItemStack result = recipe.getCraftedItemStack().copy();
            boolean bl = serverPlayer.getInventory().add(result);
            ItemEntity itemEntity;
            if(!bl && !result.isEmpty()) {
                itemEntity = serverPlayer.drop(result, false);
                if (itemEntity != null) {
                    itemEntity.setNoPickUpDelay();
                    itemEntity.setOwner(serverPlayer.getUUID());
                }
            }

        }
    }


}
