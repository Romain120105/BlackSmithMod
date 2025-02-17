package fr.shoqapik.btemobs.client.renderer.entity;

import fr.shoqapik.btemobs.client.model.BlacksmithModel;
import fr.shoqapik.btemobs.entity.BlacksmithEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlacksmithEntityRenderer extends GeoEntityRenderer<BlacksmithEntity> {

    public BlacksmithEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BlacksmithModel());
    }

}
