package fr.shoqapik.btemobs.client.model;

import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.entity.BlacksmithEntity;
import fr.shoqapik.btemobs.entity.ExplorerEntity;
import fr.shoqapik.btemobs.entity.WarlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ExplorerModel extends AnimatedGeoModel<ExplorerEntity> {

    @Override
    public ResourceLocation getModelResource(ExplorerEntity explorerEntity) {
        return new ResourceLocation(BteMobsMod.MODID, "geo/blacksmith.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ExplorerEntity explorerEntity) {
        return new ResourceLocation(BteMobsMod.MODID, "textures/entity/blacksmith.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ExplorerEntity explorerEntity) {
        return new ResourceLocation(BteMobsMod.MODID, "animations/blacksmith.animation.json");
    }
}
