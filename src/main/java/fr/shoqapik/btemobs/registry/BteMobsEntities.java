package fr.shoqapik.btemobs.registry;

import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.entity.BlacksmithEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BteMobsEntities {

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BteMobsMod.MODID);

    public static final RegistryObject<EntityType<BlacksmithEntity>> BLACKSMITH_ENTITY = ENTITIES.register("blacksmith",
            () -> EntityType.Builder.of(BlacksmithEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F).fireImmune().updateInterval(1).build(BteMobsMod.MODID + ":blacksmith"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
