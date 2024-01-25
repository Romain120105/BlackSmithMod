package fr.shoqapik.blacksmithmod.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.quests.QuestAnswer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class QuestDialogScreen extends Screen {
    public static final ResourceLocation DIALOGS_LOCATION = new ResourceLocation(BlackSmithMod.MODID, "textures/gui/dialogs.png");
    private String entityname;
    private List<String> dialogs = new ArrayList<>();
    private boolean typing;
    private int letterIndex;
    private String currentLine = "";
    private int page;

    private List<QuestAnswer> questAnswers;
    private List<Button> buttons = new ArrayList<>();
    private boolean declined;

/*    private Button acceptQuestButton;
    private Button declineQuestButton;
  */

    public QuestDialogScreen(String entityName, List<String> dialogs, List<QuestAnswer> questAnswers) {
        super(Component.literal(entityName));
        this.entityname = entityName;
        this.dialogs = dialogs;
        this.questAnswers = questAnswers;
    }

    @Override
    protected void init() {
        int x = this.width / 2;
        int y = this.height - 75;
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (index < questAnswers.size()) {
                    QuestAnswer questAnswer = questAnswers.get(index);
                    buttons.add(this.addRenderableWidget(new Button(380 + i * 110, y + j * 25, 100, 20, Component.literal(questAnswer.getFormattedAwnser()), (p_95981_) -> {
                        this.dialogs.add("Not implemented yet! Come back later.");
                        this.page = page + 1;
                        this.declined = true;
                        setButtonsEnabled(false);
                        letterIndex = 0;
                        currentLine = "";
                    })));
                    index += 1;
                }
            }
        }
        setButtonsEnabled(false);

        super.init();

    }

    @Override
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        if (letterIndex < dialogs.get(page).length()) {
            typing = true;
            currentLine += dialogs.get(page).charAt(letterIndex);
            letterIndex += 1;
        } else {
            typing = false;
        }
        this.setFocused(null);
        int imageWidth = 254;
        int imageHeight = 60;
        int x = 120;
        int y = this.height - 90;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, DIALOGS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        //blit(p_96562_, x, y, 0, 0, 620, 100, 1024, 1024);
        blit(p_96562_, x, y, 0, 0, imageWidth, imageHeight, 512, 512);
        //blit(p_96562_, x, y, 0, 0, 240, 120, 512,512);

        drawCenteredString(p_96562_, font, entityname, x + imageWidth / 2, height - 100 + 15, 16777215);

        drawWordWrap(Component.literal(currentLine), x + 5, height - 80 + 15, 240, 16777215, font, p_96562_);


        p_96562_.popPose();
        if (!typing) {
            if (page != dialogs.size() - 1 || declined) {
                drawCenteredString(p_96562_, font, "Click anywhere to continue", x + imageWidth / 2, height - 120 + 15, 16777215);
            } else {
                setButtonsEnabled(true);
            }
        }

        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
    }

    public void drawWordWrap(FormattedText p_92858_, int p_92859_, int p_92860_, int p_92861_, int p_92862_, Font font, PoseStack stack) {
        Matrix4f matrix4f = stack.last().pose();

        for (FormattedCharSequence formattedcharsequence : font.split(p_92858_, p_92861_)) {
            font.drawInternal(formattedcharsequence, (float) p_92859_, (float) p_92860_, p_92862_, matrix4f, false);
            p_92860_ += 11;
        }

    }


    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        if (typing) {
            typing = false;
            currentLine = dialogs.get(page);
            letterIndex = currentLine.length();
        } else if (page != dialogs.size() - 1) {
            page += 1;
            letterIndex = 0;
            currentLine = "";
        } else if (declined) {
            Minecraft.getInstance().setScreen(null);
        } else if (page == dialogs.size() - 1) {
            return super.mouseClicked(p_94695_, p_94696_, p_94697_);
        }
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void setButtonsEnabled(boolean enabled) {
        for (Button button : buttons) {
            button.visible = enabled;
            button.active = enabled;
        }
    }
}
