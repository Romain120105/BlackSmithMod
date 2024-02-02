package fr.shoqapik.blacksmithmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.client.BlackSmithModClient;
import fr.shoqapik.blacksmithmod.client.ClientRecipeLocker;
import fr.shoqapik.blacksmithmod.client.widget.CategoryButton;
import fr.shoqapik.blacksmithmod.client.widget.SmithRecipeButton;
import fr.shoqapik.blacksmithmod.client.widget.SmithStateSwitchingButton;
import fr.shoqapik.blacksmithmod.menu.SmithCraftMenu;
import fr.shoqapik.blacksmithmod.packets.PlaceRecipePacket;
import fr.shoqapik.blacksmithmod.recipe.BlackSmithRecipe;
import fr.shoqapik.blacksmithmod.recipe.RecipeCategory;
import fr.shoqapik.blacksmithmod.recipe.RecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SmithCraftScreen extends AbstractContainerScreen<SmithCraftMenu> {
    public static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation(BlackSmithMod.MODID, "textures/gui/blacksmith_screen.png");
    private RecipeCategory currentCategory = RecipeCategory.ALL;
    private EditBox searchBox;
    private String previousSearchText = "";
    private List<SmithRecipeButton> buttonList = new ArrayList<SmithRecipeButton>();
    private StateSwitchingButton forwardButton;
    private StateSwitchingButton backButton;
    private List<BlackSmithRecipe> categoryRecipes = RecipeManager.getRecipesFor(RecipeCategory.ALL);
    private int page = 0;
    private SmithRecipeButton hoveredButton;
    private List<CategoryButton> tabButtons = new ArrayList<>();
    private Button craftButton;
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
        this.forwardButton = new SmithStateSwitchingButton(i + 93, j + 137, 12, 17, false);
        this.forwardButton.initTextureValues(1, 182, 13, 18, CRAFTING_TABLE_LOCATION);
        this.backButton = new SmithStateSwitchingButton(i + 38, j + 137, 12, 17, true);
        this.backButton.initTextureValues(1, 182, 13, 18, CRAFTING_TABLE_LOCATION);
        tabButtons.clear();
        for(RecipeCategory category : RecipeCategory.values()){
            this.tabButtons.add(new CategoryButton(category));
        }
        refreshButtons();
        updateTabs();
        this.craftButton = new Button(i + 250, j + 60, 65, 20, Component.literal("Craft"), new Button.OnPress() {
            @Override
            public void onPress(Button p_93751_) {
                Minecraft.getInstance().setScreen(null);
            }
        });
        this.craftButton.active = false;


    }

    private void refreshButtons(){
        int i = (this.width - 147) / 2 - 86;
        int j = (this.height - 166) / 2;
        if(page < 0) page = 0;
        if(page * 20 > categoryRecipes.size()) page -= 1;
        categoryRecipes = RecipeManager.getRecipesFor(currentCategory);
        removeLockedRecipes();
        if(!searchBox.getValue().isEmpty()){
            filterRecipes();
        }
        this.buttonList.clear();
        for(int index = page * 20; index < (page+1) * 20; ++index) {
            if(index >= categoryRecipes.size()) break;
            BlackSmithRecipe recipe = categoryRecipes.get(index);
            SmithRecipeButton button = new SmithRecipeButton(i+11, j+31, recipe);
            int moduloIndex = index % 20;
            button.setPosition(i + 6 + 25 * (moduloIndex % 5), j + 31 + 25 * (moduloIndex / 5));
            buttonList.add(button);
            button.setHasEnough(recipe.hasItems(this.minecraft.player));

        }
    }

    private void updateTabs() {
        int i = (this.width - 147) / 2 - 90 - 30;
        int j = (this.height - 166) / 2 + 3;
        int k = 27;
        int l = 0;

        for(CategoryButton recipebooktabbutton : this.tabButtons) {
            recipebooktabbutton.setPosition(i, j + 27 * l++);
            recipebooktabbutton.setStateTriggered(recipebooktabbutton.getCategory() == currentCategory);
        }

    }

    private void removeLockedRecipes(){
        List<BlackSmithRecipe> toRemove = new ArrayList<>();
        for(BlackSmithRecipe recipe : categoryRecipes){
            if(!ClientRecipeLocker.get().hasRecipe(recipe.getCraftedItem())) {
                toRemove.add(recipe);
            }
        }
        for (BlackSmithRecipe recipe : toRemove){
            categoryRecipes.remove(recipe);
        }
    }

    private void filterRecipes(){
        List<BlackSmithRecipe> toRemove = new ArrayList<>();
        for(BlackSmithRecipe recipe : categoryRecipes){
            if(!recipe.getCraftedItemStack().getDisplayName().getString().toLowerCase().contains(this.searchBox.getValue().toLowerCase())) {
                toRemove.add(recipe);
            }
        }
        for (BlackSmithRecipe recipe : toRemove){
            categoryRecipes.remove(recipe);
        }
    }

    @Override
    public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        for(CategoryButton button : tabButtons){
            button.render(p_97795_, p_97796_, p_97797_, p_97798_);
        }
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        int i = (this.width - 147) / 2 - 86;
        int j = (this.height - 166) / 2;
        if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
            drawString(p_97795_, this.minecraft.font, "Search recipe", i + 25, j + 14, -1);
        } else {
            this.searchBox.render(p_97795_, p_97796_, p_97797_, p_97798_);
        }
        hoveredButton = null;
        for(SmithRecipeButton button : buttonList) {
            button.render(p_97795_, p_97796_, p_97797_, p_97798_);
            if(button.isHoveredOrFocused()){
                hoveredButton = button;
            }
        }

        this.backButton.render(p_97795_, p_97796_, p_97797_, p_97798_);
        this.forwardButton.render(p_97795_, p_97796_, p_97797_, p_97798_);
        if(previousSearchText != searchBox.getValue()){
            previousSearchText = searchBox.getValue();
            page = 0;
            refreshButtons();
        }

        renderTooltip(p_97795_, p_97796_, p_97797_);
        this.craftButton.render(p_97795_, p_97796_, p_97797_, p_97798_);
    }

    public void renderTooltip(PoseStack p_100418_, int p_100419_, int p_100420_) {
        if (this.minecraft.screen != null && this.hoveredButton != null) {
            this.minecraft.screen.renderComponentTooltip(p_100418_, this.hoveredButton.getTooltipText(this.minecraft.screen), p_100419_, p_100420_, this.hoveredButton.getRecipeStack());
        }

    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CRAFTING_TABLE_LOCATION);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        blit(p_97787_, i, j, 0, 0, this.imageWidth, this.imageHeight, 512, 512);
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
        }else if(forwardButton.mouseClicked(p_97748_, p_97749_, p_97750_)){
            page+=1;
            refreshButtons();
            return true;
        }else if(backButton.mouseClicked(p_97748_, p_97749_, p_97750_)){
            page-=1;
            refreshButtons();
            return true;
        }
        for(CategoryButton button : tabButtons){
            if(button.mouseClicked(p_97748_, p_97749_, p_97750_)){
                if(button.getCategory() != currentCategory) {
                    this.searchBox.setValue("");
                    this.page = 0;
                    this.currentCategory = button.getCategory();
                    refreshButtons();
                    updateTabs();
                }
            }
        }
        for(SmithRecipeButton button : buttonList) {
            if (button.mouseClicked(p_97748_, p_97749_, p_97750_) && button.hasEnough) {
                BlackSmithMod.sendToServer(new PlaceRecipePacket(button.getRecipe().getCraftedItem()));
                this.craftButton.active = true;
            }
        }
        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }

    @Override
    public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
        if(this.searchBox.keyPressed(p_97765_, p_97766_, p_97767_)){
            return true;
        }
        return searchBox.isFocused() || super.keyPressed(p_97765_, p_97766_, p_97767_);
    }

    @Override
    public boolean charTyped(char p_94683_, int p_94684_) {
       if(this.searchBox.charTyped(p_94683_, p_94684_)){
           page = 0;
           refreshButtons();
           return true;
       }
       return super.charTyped(p_94683_, p_94684_);
    }
}
