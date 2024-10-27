package fr.shoqapik.btemobs.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.shoqapik.btemobs.BteMobsMod;
import fr.shoqapik.btemobs.menu.BlacksmithRepairMenu;
import fr.shoqapik.btemobs.packets.RenameItemPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class BlacksmithRepairScreen extends ItemCombinerScreen<BlacksmithRepairMenu> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(BteMobsMod.MODID, "textures/gui/container/blacksmith_repair.png");
    private final Player player;
    private EditBox name;

    public BlacksmithRepairScreen(BlacksmithRepairMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, TEXTURE_LOCATION);
        this.player = pPlayerInventory.player;
        this.titleLabelX = 60;
    }


    public void containerTick() {
        super.containerTick();
        this.name.tick();
    }

    protected void subInit() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.name = new EditBox(this.font, i + 62, j + 24, 103, 12, Component.translatable("container.repair"));
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setResponder(this::onNameChanged);
        this.name.setValue("");
        this.addWidget(this.name);
        this.setInitialFocus(this.name);
        this.name.setEditable(false);
    }

    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.name.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.name.setValue(s);
    }

    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return !this.name.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.name.canConsumeInput() ? super.keyPressed(pKeyCode, pScanCode, pModifiers) : true;
    }

    private void onNameChanged(String p_97899_) {
        if (!p_97899_.isEmpty()) {
            String s = p_97899_;
            Slot slot = this.menu.getSlot(0);
            if (slot != null && slot.hasItem() && !slot.getItem().hasCustomHoverName() && p_97899_.equals(slot.getItem().getHoverName().getString())) {
                s = "";
            }

            this.menu.setItemName(s);
            BteMobsMod.INSTANCE.sendToServer(new RenameItemPacket(s));
        }
    }

    public void renderFg(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.name.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot.
     */
    public void slotChanged(AbstractContainerMenu pContainerToSend, int pSlotInd, ItemStack pStack) {
        if (pSlotInd == 0) {
            this.name.setValue(pStack.isEmpty() ? "" : pStack.getHoverName().getString());
            this.name.setEditable(!pStack.isEmpty());
            this.setFocused(this.name);
        }

    }
}
