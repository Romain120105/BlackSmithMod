package fr.shoqapik.blacksmithmod.client.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.recipe.BlackSmithRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SmithRecipeButton extends AbstractWidget {
    private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation(BlackSmithMod.MODID, "textures/gui/blacksmith_screen.png");

    private Minecraft minecraft = Minecraft.getInstance();
    private ItemStack recipeStack;
    public SmithRecipeButton(int x, int y, BlackSmithRecipe recipe) {
        super(x, y, 25, 25, CommonComponents.EMPTY);
        this.recipeStack = recipe.getCraftedItemStack();
    }

    public SmithRecipeButton(int p_93629_, int p_93630_, int p_93631_, int p_93632_, Component p_93633_) {
        super(p_93629_, p_93630_, p_93631_, p_93632_, p_93633_);
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {

    }

    @Override
    public void renderButton(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CRAFTING_TABLE_LOCATION);
        int i = 29;
        int j = 205;
        System.out.println(this.x +", "+ this.y +", "+i+ ", " + j+ ", " + this.width + ", " + this.height);
        blit(p_93676_, this.x, this.y, i, j, this.width, this.height, 512, 512);

        minecraft.getItemRenderer().renderAndDecorateItem(recipeStack, this.x + 4+1, this.y + 4+1, 0, 10);
        minecraft.getItemRenderer().renderAndDecorateFakeItem(recipeStack, this.x + 4, this.y + 4);

    }
}
