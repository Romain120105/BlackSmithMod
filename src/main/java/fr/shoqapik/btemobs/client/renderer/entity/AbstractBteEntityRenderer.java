package fr.shoqapik.btemobs.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.shoqapik.btemobs.entity.BteAbstractEntity;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractBteEntityRenderer<T extends BteAbstractEntity> extends MobRenderer<T, VillagerModel<T>> {

    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");

    public AbstractBteEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerModel<>(context.bakeLayer(ModelLayers.VILLAGER)), 0.5F);

        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        // TODO: this.addLayer(new VillagerProfessionLayer(this, context.getResourceManager(), "villager"));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(T t) {
        return VILLAGER_BASE_SKIN;
    }

    public abstract ResourceLocation getLayerTexture();

    protected void scale(BteAbstractEntity p_116314_, PoseStack p_116315_, float p_116316_) {
        float f = 0.9375F;
        this.shadowRadius = 0.5F;

        p_116315_.scale(f, f, f);
    }
}