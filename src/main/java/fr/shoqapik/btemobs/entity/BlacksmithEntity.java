package fr.shoqapik.btemobs.entity;

import fr.shoqapik.btemobs.registry.BteMobsBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BlacksmithEntity extends BteAbstractEntity {

    public BlacksmithEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public BteNpcType getNpcType() {
        return BteNpcType.BLACKSMITH;
    }

    @Override
    public Block getWorkBlock() {
        return BteMobsBlocks.MAGMA_FORGE.get();
    }
}
