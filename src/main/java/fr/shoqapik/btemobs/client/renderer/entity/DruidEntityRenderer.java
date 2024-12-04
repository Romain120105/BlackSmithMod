package fr.shoqapik.btemobs.client.renderer.entity;

import fr.shoqapik.btemobs.client.model.DruidModel;
import fr.shoqapik.btemobs.client.model.ExplorerModel;
import fr.shoqapik.btemobs.entity.DruidEntity;
import fr.shoqapik.btemobs.entity.ExplorerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DruidEntityRenderer extends GeoEntityRenderer<DruidEntity> {

    public DruidEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DruidModel());
    }

}
