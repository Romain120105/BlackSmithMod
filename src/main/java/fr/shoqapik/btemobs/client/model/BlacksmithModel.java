package fr.shoqapik.btemobs.client.model;

import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.entity.BlacksmithEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlacksmithModel extends AnimatedGeoModel<BlacksmithEntity> {

    @Override
    public ResourceLocation getModelResource(BlacksmithEntity blacksmithEntity) {
        return new ResourceLocation(BteMobsMod.MODID, "geo/blacksmith.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlacksmithEntity blacksmithEntity) {
        return new ResourceLocation(BteMobsMod.MODID, "textures/entity/blacksmith.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlacksmithEntity blacksmithEntity) {
        return new ResourceLocation(BteMobsMod.MODID, "animations/blacksmith.animation.json");
    }
}
