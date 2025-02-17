package fr.shoqapik.btemobs.block;

import fr.shoqapik.btemobs.blockentity.MagmaForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MagmaForgeBlock extends BteAbstractWorkBlock {

    public MagmaForgeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MagmaForgeBlockEntity(pPos, pState);
    }
}
