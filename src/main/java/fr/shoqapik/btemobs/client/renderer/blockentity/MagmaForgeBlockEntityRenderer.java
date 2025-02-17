package fr.shoqapik.btemobs.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fr.shoqapik.btemobs.blockentity.BteAbstractWorkBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class MagmaForgeBlockEntityRenderer implements BlockEntityRenderer<BteAbstractWorkBlockEntity> {

    private final ItemRenderer itemRenderer;

    public MagmaForgeBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BteAbstractWorkBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.getCapability(BteAbstractWorkBlockEntity.ITEM_HANDLER).resolve().isPresent()) {
            ItemStack stack = pBlockEntity.getCapability(BteAbstractWorkBlockEntity.ITEM_HANDLER).resolve().get().getStackInSlot(0);

            if(stack != ItemStack.EMPTY) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5D, 1.1D, 0.5D);
                pPoseStack.scale(0.5F, 0.5F, 0.5F);
                pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));
                pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(-45));
                this.itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, 15728850, OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, (int)pBlockEntity.getBlockPos().asLong());
                pPoseStack.popPose();
            }
        }
    }
}
