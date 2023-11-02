package fr.shoqapik.blacksmithmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.shoqapik.blacksmithmod.BlackSmithMod;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BlackSmithDialogScreen extends Screen {
    public static final ResourceLocation DIALOGS_LOCATION = new ResourceLocation(BlackSmithMod.MODID,"textures/gui/dialogs.png");
    private boolean typing;
    private int letterIndex;
    private String currentLine = "";
    private int page;

    private String dialog;

/*    private Button acceptQuestButton;
    private Button declineQuestButton;
  */


    public BlackSmithDialogScreen() {
        super(Component.literal("BlackSmith Dialogs"));
        this.dialog = "Hello young traveler! What can I do for you?";
    }

    @Override
    protected void init() {
        int x = this.width / 2;
        int y = this.height - 130;
        /*this.acceptQuestButton = this.addRenderableWidget(new Button(x +5, y, 100, 20, Component.literal("Accept Quest"), (p_95981_) -> {
            NewNpcMod.sendToServer(new AcceptQuestPacket(npcName));
        }));

        this.declineQuestButton = this.addRenderableWidget(new Button(x -105, y, 100, 20, Component.literal("Decline Quest"), (p_95981_) -> {
            this.dialogs.add("All right! You can always come back later if you change your mind.");
            this.page = page+1;
            this.declined = true;
            setButtonsEnabled(false);
            letterIndex = 0;
            currentLine = "";
        }));*/

        setButtonsEnabled(false);

        super.init();

    }

    @Override
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        if(letterIndex < dialog.length()){
            typing = true;
            currentLine += dialog.charAt(letterIndex);
            letterIndex += 1;
        }else{
            typing = false;
        }
        //this.renderBackground(p_96562_);
        this.setFocused(null);

        int x = this.width / 2 - (240/2);
        int y = this.height - 105;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, DIALOGS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(p_96562_, x, y, 0, 0, 260, 120);

        //blit(p_96562_, x, y, 0, 0, 240, 120, 512,512);

        font.drawWordWrap(Component.literal(currentLine), width / 2 - 250, height - 100, 500, 16777215);

        super.render(p_96562_,p_96563_, p_96564_, p_96565_);
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void setButtonsEnabled(boolean enabled){
        /*
        this.acceptQuestButton.visible = enabled;
        this.acceptQuestButton.active = enabled;
        this.declineQuestButton.visible = enabled;
        this.declineQuestButton.active = enabled;*/
    }
}
