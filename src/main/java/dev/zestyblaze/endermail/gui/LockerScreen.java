package dev.zestyblaze.endermail.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.zestyblaze.endermail.EnderMail;
import dev.zestyblaze.endermail.block.LockerBlock;
import dev.zestyblaze.endermail.gui.container.LockerMenu;
import dev.zestyblaze.endermail.network.ConfigureLockerPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;

@Environment(EnvType.CLIENT)
public class LockerScreen extends AbstractContainerScreen<LockerMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(EnderMail.MODID, "textures/gui/locker.png");

    private EditBox idTextField;

    private BlockPos lockerPos;
    private String lockerID;

    public LockerScreen(LockerMenu containerLocker, Inventory playerInventory, Component title) {
        super(containerLocker, playerInventory, title);
        lockerPos = containerLocker.getLockerPos();
        lockerID = containerLocker.getLockerID();
        imageHeight = 133;
        inventoryLabelY = imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        setupTextFields();
    }

    @Override
    public void containerTick() {
        super.containerTick();
        idTextField.tick();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        idTextField.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int par2, int par3) {
        super.renderLabels(poseStack, par2, par3);
        font.draw(poseStack, Component.translatable("string.endermail.id"), 75, titleLabelY, 4210752);
    }

    @Override
    public void onClose() {
        if (!idTextField.getValue().isEmpty() && !idTextField.getValue().equals(lockerID)) {
            EnderMail.network.sendToServer(new ConfigureLockerPacket(lockerPos, idTextField.getValue()));
        }
        super.onClose();
    }

    @Override
    public boolean keyPressed(int par1, int par2, int par3) {
        if (par1 == 256) {
            onClose();
            return true;
        } else if (par1 == 258) {
            boolean flag = !hasShiftDown();
            if (!changeFocus(flag)) {
                changeFocus(flag);
            }
            return true;
        } else if (getFocused() != null && getFocused().keyPressed(par1, par2, par3)) {
            return true;
        }
        InputConstants.Key mouseKey = InputConstants.getKey(par1, par2);
        if (!idTextField.isFocused() && (minecraft.options.keyInventory.isDown() && minecraft.options.keyInventory.key == mouseKey)) {
            onClose();
            return true;
        } else {
            boolean handled = checkHotbarKeyPressed(par1, par2);
            if (hoveredSlot != null && hoveredSlot.hasItem()) {
                if (minecraft.options.keyPickItem.isDown() && minecraft.options.keyPickItem.key == mouseKey) {
                    slotClicked(hoveredSlot, hoveredSlot.index, 0, ClickType.CLONE);
                    handled = true;
                } else if (minecraft.options.keyDrop.isDown() && minecraft.options.keyDrop.key == mouseKey) {
                    slotClicked(hoveredSlot, hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
                    handled = true;
                }
            } else if (minecraft.options.keyDrop.isDown() && minecraft.options.keyDrop.key == mouseKey) {
                handled = true;
            }

            return handled;
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        blit(poseStack, i, j, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void resize(Minecraft mc, int par2, int par3) {
        String s = idTextField.getValue();
        init(mc, par2, par3);
        idTextField.setValue(s);
    }

    private void setupTextFields() {
        clearWidgets();
        idTextField = new EditBox(font, (width - imageWidth) / 2 + 75, (height - imageHeight) / 2 + 20, 80, 18, Component.literal(""));
        idTextField.setMaxLength(LockerBlock.MAX_ID_LENGTH);
        idTextField.setValue(lockerID);
        addWidget(idTextField);
    }

}
