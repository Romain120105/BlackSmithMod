package fr.shoqapik.btemobs.blockentity;

import fr.shoqapik.btemobs.registry.BteMobsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class MagmaForgeBlockEntity extends BlockEntity {

    public static Capability<IItemHandler> ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<>(){});

    private ItemStackHandler itemHandler = new ItemStackHandler();
    private final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> this.itemHandler);

    public MagmaForgeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BteMobsBlockEntities.MAGMA_FORGE.get(), pPos, pBlockState);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction direction) {
        if (capability == ITEM_HANDLER) {
            return this.inventoryOptional.cast();
        }
        return super.getCapability(capability, direction); // See note after snippet
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.inventoryOptional.invalidate();
    }

    @Override
    public void setChanged() {

    }

}
