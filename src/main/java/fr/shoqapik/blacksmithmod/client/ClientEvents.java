package fr.shoqapik.blacksmithmod.client;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.recipe.RecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlackSmithMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    private int previousTimesChanged = 0;
    private Minecraft minecraft = Minecraft.getInstance();
    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event) {
        Inventory inventory = minecraft.player.getInventory();
        if(inventory.getTimesChanged() != previousTimesChanged){
            previousTimesChanged = inventory.getTimesChanged();
            if(detectNewItems(inventory)){
                System.out.println("new items discovered");
            }
        }
    }

    private boolean detectNewItems(Inventory inventory){
        if(ClientRecipeLocker.get() == null){
            new ClientRecipeLocker();
        }
        boolean discovered = false;
        for(ItemStack stack : inventory.items) {
            if(RecipeManager.isRecipeItem(stack)){
                ClientRecipeLocker.get().discoverItem(stack);
                discovered = true;
            }
        }
        return discovered;
    }




}
