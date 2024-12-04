package fr.shoqapik.btemobs.entity;

import fr.shoqapik.btemobs.registry.BteMobsBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ExplorerEntity extends BteAbstractEntity {

    public ExplorerEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public BteNpcType getNpcType() {
        return BteNpcType.EXPLORER;
    }

    @Override
    public Block getWorkBlock() {
        return BteMobsBlocks.MAGMA_FORGE.get();
    }
}
