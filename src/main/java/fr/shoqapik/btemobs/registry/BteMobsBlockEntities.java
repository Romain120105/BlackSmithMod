package fr.shoqapik.btemobs.registry;

import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.block.MagmaForgeBlock;
import fr.shoqapik.btemobs.blockentity.MagmaForgeBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BteMobsBlockEntities {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BteMobsMod.MODID);

    public static final RegistryObject<BlockEntityType<MagmaForgeBlockEntity>> MAGMA_FORGE = BLOCK_ENTITIES.register("magma_forge",
            () -> BlockEntityType.Builder.of(MagmaForgeBlockEntity::new, BteMobsBlocks.MAGMA_FORGE.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
