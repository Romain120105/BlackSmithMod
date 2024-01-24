package fr.shoqapik.blacksmithmod.client;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.client.gui.QuestDialogScreen;
import fr.shoqapik.blacksmithmod.client.renderer.BlackSmithEntityRenderer;
import fr.shoqapik.blacksmithmod.packets.ShowDialogPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = BlackSmithMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlackSmithModClient {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BlackSmithMod.BLACKSMITH_ENTITY.get(), BlackSmithEntityRenderer::new);
    }


    public static void handleDialogPacket(ShowDialogPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().setScreen(new QuestDialogScreen(msg.entityName, msg.dialogs, msg.answers));
    }
}
