package fr.shoqapik.blacksmithmod.client;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.recipe.RecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlackSmithMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    private static int previousTimesChanged = 0;
    private static Minecraft minecraft = Minecraft.getInstance();
    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {
        if(minecraft.player != null) {
            Inventory inventory = minecraft.player.getInventory();
            if (inventory.getTimesChanged() != previousTimesChanged) {
                previousTimesChanged = inventory.getTimesChanged();
                detectNewItems(inventory);
            }
        }
    }

    private static void detectNewItems(Inventory inventory){
        if(ClientRecipeLocker.get() == null){
            new ClientRecipeLocker();
        }
        for(ItemStack stack : inventory.items) {
            if(RecipeManager.isRecipeItem(stack) && stack.getItem() != Items.AIR){
                ClientRecipeLocker.get().discoverItem(stack);

            }
        }
    }




}
