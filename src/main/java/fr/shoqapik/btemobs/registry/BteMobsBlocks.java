package fr.shoqapik.btemobs.registry;

import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.block.MagmaForgeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BteMobsBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BteMobsMod.MODID);

    public static final RegistryObject<MagmaForgeBlock> MAGMA_FORGE = BLOCKS.register("magma_forge",
            () -> new MagmaForgeBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).noOcclusion().lightLevel((state) -> 9)));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
