package fr.shoqapik.btemobs.blockentity;

import fr.shoqapik.btemobs.registry.BteMobsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MagmaForgeBlockEntity extends BteAbstractWorkBlockEntity {

    public MagmaForgeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BteMobsBlockEntities.MAGMA_FORGE.get(), pPos, pBlockState);
    }
}
