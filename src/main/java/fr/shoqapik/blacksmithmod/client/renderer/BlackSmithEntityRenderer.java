package fr.shoqapik.blacksmithmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.shoqapik.blacksmithmod.entity.BlackSmithEntity;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;

public class BlackSmithEntityRenderer extends MobRenderer<BlackSmithEntity, VillagerModel<BlackSmithEntity>> {
    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");

    public BlackSmithEntityRenderer(EntityRendererProvider.Context p_174437_) {
        super(p_174437_, new VillagerModel<>(p_174437_.bakeLayer(ModelLayers.VILLAGER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, p_174437_.getModelSet(), p_174437_.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, p_174437_.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(BlackSmithEntity p_116312_) {
        return VILLAGER_BASE_SKIN;
    }

    protected void scale(BlackSmithEntity p_116314_, PoseStack p_116315_, float p_116316_) {
        float f = 0.9375F;
        this.shadowRadius = 0.5F;

        p_116315_.scale(f, f, f);
    }
}