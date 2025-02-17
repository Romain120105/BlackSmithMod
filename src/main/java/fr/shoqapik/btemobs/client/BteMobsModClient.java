package fr.shoqapik.btemobs.client;

import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.client.gui.BlacksmithRepairScreen;
import fr.shoqapik.btemobs.client.gui.QuestDialogScreen;
import fr.shoqapik.btemobs.client.gui.BlacksmithCraftScreen;
import fr.shoqapik.btemobs.client.renderer.blockentity.MagmaForgeBlockEntityRenderer;
import fr.shoqapik.btemobs.client.renderer.entity.BlacksmithEntityRenderer;
import fr.shoqapik.btemobs.client.renderer.entity.DruidEntityRenderer;
import fr.shoqapik.btemobs.client.renderer.entity.ExplorerEntityRenderer;
import fr.shoqapik.btemobs.client.renderer.entity.WarlockEntityRenderer;
import fr.shoqapik.btemobs.entity.BteAbstractEntity;
import fr.shoqapik.btemobs.entity.ExplorerEntity;
import fr.shoqapik.btemobs.menu.BlacksmithCraftMenu;
import fr.shoqapik.btemobs.packets.*;
import fr.shoqapik.btemobs.registry.BteMobsBlockEntities;
import fr.shoqapik.btemobs.registry.BteMobsContainers;
import fr.shoqapik.btemobs.registry.BteMobsEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = BteMobsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BteMobsModClient {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(BteMobsContainers.BLACKSMITH_CRAFT_MENU.get(), BlacksmithCraftScreen::new);
        MenuScreens.register(BteMobsContainers.BLACKSMITH_REPAIR_MENU.get(), BlacksmithRepairScreen::new);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BteMobsEntities.BLACKSMITH_ENTITY.get(), BlacksmithEntityRenderer::new);
        event.registerEntityRenderer(BteMobsEntities.WARLOCK_ENTITY.get(), WarlockEntityRenderer::new);
        event.registerEntityRenderer(BteMobsEntities.EXPLORER_ENTITY.get(), ExplorerEntityRenderer::new);
        event.registerEntityRenderer(BteMobsEntities.DRUID_ENTITY.get(), DruidEntityRenderer::new);
        event.registerBlockEntityRenderer(BteMobsBlockEntities.MAGMA_FORGE.get(), MagmaForgeBlockEntityRenderer::new);
    }

    public static void handleActionPacket(ActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if(msg.actionType.equals("start_crafting")) {
            BteAbstractEntity entity = (BteAbstractEntity) Minecraft.getInstance().level.getEntity(msg.entityId);
            entity.setCrafting(true);
        }
    }

    public static void handleDialogPacket(ShowDialogPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().setScreen(new QuestDialogScreen(msg.entityId, msg.bteNpcType, msg.quest));
    }

    public static void handleToggleCraftButtonPacket(ToggleCraftButton msg, Supplier<NetworkEvent.Context> ctx) {
        Screen screen = Minecraft.getInstance().screen;
        if(screen != null && screen instanceof BlacksmithCraftScreen) {
            ((BlacksmithCraftScreen) screen).setCraftButtonActive(msg.active);
        }
    }

    public static void handlePlaceGhostRecipe(PlaceGhostRecipePacket msg, Supplier<NetworkEvent.Context> ctx) {
        AbstractContainerMenu containerMenu = Minecraft.getInstance().player.containerMenu;
        if (containerMenu.containerId == msg.getContainerId() && containerMenu instanceof BlacksmithCraftMenu) {
            Minecraft.getInstance().getConnection().getRecipeManager().byKey(msg.getRecipe()).ifPresent((recipe) -> {
                if (Minecraft.getInstance().screen instanceof RecipeUpdateListener) {
                    RecipeBookComponent recipebookcomponent = ((RecipeUpdateListener)Minecraft.getInstance().screen).getRecipeBookComponent();
                    recipebookcomponent.setupGhostRecipe(recipe, containerMenu.slots);
                }

            });
        }
    }
}
