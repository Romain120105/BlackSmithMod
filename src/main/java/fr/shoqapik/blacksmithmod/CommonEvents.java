package fr.shoqapik.blacksmithmod;

import fr.shoqapik.blacksmithmod.packets.ShowDialogPacket;
import fr.shoqapik.blacksmithmod.quests.Quest;
import fr.shoqapik.blacksmithmod.quests.QuestManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BlackSmithMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void entityClickEvent(PlayerInteractEvent.EntityInteract event){
        ResourceLocation entityId= ForgeRegistries.ENTITY_TYPES.getKey(event.getTarget().getType());
        Quest quest = QuestManager.getQuest(entityId);
        if(quest != null && event.getEntity() instanceof ServerPlayer){
            BlackSmithMod.sendToClient(new ShowDialogPacket(event.getTarget().getDisplayName().getString(), quest.getDialogs(), quest.getAnswers()), (ServerPlayer) event.getEntity());

        }
    }

    @SubscribeEvent
    public static void addQuestsData(AddReloadListenerEvent event){
        event.addListener(new QuestManager());
    }


}
