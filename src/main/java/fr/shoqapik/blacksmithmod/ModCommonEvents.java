package fr.shoqapik.blacksmithmod;

import fr.shoqapik.blacksmithmod.entity.BlackSmithEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlackSmithMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(BlackSmithMod.BLACKSMITH_ENTITY.get(), BlackSmithEntity.getBlacksmithAttributes().build());
    }

}
