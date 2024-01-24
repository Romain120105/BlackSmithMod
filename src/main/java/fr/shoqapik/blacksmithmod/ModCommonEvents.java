package fr.shoqapik.blacksmithmod;

import fr.shoqapik.blacksmithmod.entity.BlackSmithEntity;
import fr.shoqapik.blacksmithmod.packets.ShowDialogPacket;
import fr.shoqapik.blacksmithmod.quests.Quest;
import fr.shoqapik.blacksmithmod.quests.QuestManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BlackSmithMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(BlackSmithMod.BLACKSMITH_ENTITY.get(), BlackSmithEntity.getBlacksmithAttributes().build());
    }






}
