package fr.shoqapik.blacksmithmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.client.widget.SmithRecipeButton;
import fr.shoqapik.blacksmithmod.menu.SmithCraftMenu;
import fr.shoqapik.blacksmithmod.recipe.BlackSmithRecipe;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SmithCraftScreen extends AbstractContainerScreen<SmithCraftMenu> {
    private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation(BlackSmithMod.MODID, "textures/gui/blacksmith_screen.png");
    private EditBox searchBox;
    private SmithRecipeButton button;


    public SmithCraftScreen(SmithCraftMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        this.imageWidth = 329;
        this.imageHeight = 166;
        this.inventoryLabelX = 161;
        this.titleLabelX = 161;
    }

    protected void init() {
        super.init();
        int i = (this.width - 147) / 2 - 86;
        int j = (this.height - 166) / 2;
        String s = this.searchBox != null ? this.searchBox.getValue() : "";
        this.searchBox = new EditBox(this.minecraft.font, i + 25, j + 14, 80, 9 + 5, Component.translatable("itemGroup.search"));
        this.searchBox.setMaxLength(50);
        this.searchBox.setBordered(false);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);
        this.searchBox.setValue(s);

        BlackSmithRecipe recipe = new BlackSmithRecipe("minecraft:diamond_sword");
        button = new SmithRecipeButton(i+11, j+31, recipe);
    }

    @Override
    public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        int i = (this.width - 147) / 2 - 86;
        int j = (this.height - 166) / 2;
        if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
            drawString(p_97795_, this.minecraft.font, "Search recipe", i + 25, j + 14, -1);
        } else {
            this.searchBox.render(p_97795_, p_97796_, p_97797_, p_97798_);
        }
        button.render(p_97795_, p_97796_, p_97797_, p_97798_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CRAFTING_TABLE_LOCATION);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(p_97787_, i, j, 0, 0, this.imageWidth, this.imageHeight, 512, 512);
    }

    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        this.font.draw(p_97808_, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(p_97808_, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.searchBox.tick();
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
        if(this.searchBox.mouseClicked(p_97748_, p_97749_, p_97750_)){
            return true;
        }
        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }

    @Override
    public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
        if(this.searchBox.isFocused() && this.searchBox.keyPressed(p_97765_, p_97766_, p_97767_)){
            return true;
        }
        return super.keyPressed(p_97765_, p_97766_, p_97767_);
    }

    @Override
    public boolean charTyped(char p_94683_, int p_94684_) {
       return searchBox.isFocused() && this.searchBox.charTyped(p_94683_, p_94684_);
    }
}
