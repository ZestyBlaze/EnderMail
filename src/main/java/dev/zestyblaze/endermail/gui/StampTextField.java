package dev.zestyblaze.endermail.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.zestyblaze.endermail.util.RenderUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class StampTextField extends EditBox {

    private Font fontRenderer;
    private Component label;
    private int labelColor = 0x808080;

    private boolean pseudoIsEnabled = true;
    private boolean pseudoEnableBackgroundDrawing = true;
    private int pseudoMaxStringLength = 32;
    private int pseudoLineScrollOffset;
    private int pseudoEnabledColor = 0x6e6e6e;
    private int pseudoDisabledColor = 0xcfcfcf;
    private int pseudoCursorCounter;
    private int pseudoSelectionEnd;

    public StampTextField(Font fontRenderer, int x, int y, int width, int height, Component label) {
        super(fontRenderer, x, y, width, height, label);
        this.fontRenderer = fontRenderer;
        this.label = label;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            if (pseudoEnableBackgroundDrawing) {
                final int color = (int) (255.0F * 0.25f);
                RenderUtils.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), color / 2 << 24);
            }
            boolean showLabel = !isFocused() && getValue().isEmpty();
            int i = showLabel ? labelColor : (pseudoIsEnabled ? pseudoEnabledColor : pseudoDisabledColor);
            int j = getCursorPosition() - pseudoLineScrollOffset;
            int k = pseudoSelectionEnd - pseudoLineScrollOffset;
            String text = showLabel ? label.getString() : getValue();
            String s = fontRenderer.plainSubstrByWidth(text.substring(pseudoLineScrollOffset), width, false);
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = isFocused() && pseudoCursorCounter / 6 % 2 == 0 && flag;
            int l = pseudoEnableBackgroundDrawing ? getX() + 4 : getX();
            int i1 = pseudoEnableBackgroundDrawing ? getY() + (getHeight() - 8) / 2 : getY();
            int j1 = l;

            if (k > s.length()) {
                k = s.length();
            }

            if (!s.isEmpty()) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = fontRenderer.draw(poseStack, s1, (float) l, (float) i1, i);
            }

            boolean flag2 = getCursorPosition() < getValue().length() || getValue().length() >= pseudoMaxStringLength;
            int k1 = j1;

            if (!flag) {
                k1 = j > 0 ? l + width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }

            if (!s.isEmpty() && flag && j < s.length()) {
                j1 = fontRenderer.draw(poseStack, s.substring(j), (float) j1, (float) i1, i);
            }

            if (flag1) {
                if (flag2) {
                    RenderUtils.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + fontRenderer.lineHeight, -3092272);
                } else {
                    fontRenderer.draw(poseStack, "_", (float) k1, (float) i1, i);
                }
            }

            if (k != j) {
                int l1 = l + fontRenderer.width(s.substring(0, k));
                drawSelectionBox(k1, i1 - 1, l1 - 1, i1 + 1 + fontRenderer.lineHeight);
            }
        }
    }

    @Override
    public void setEditable(boolean enabled) {
        super.setEditable(enabled);
        pseudoIsEnabled = enabled;
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        pseudoEnabledColor = color;
    }

    @Override
    public void setTextColorUneditable(int color) {
        super.setTextColorUneditable(color);
        pseudoDisabledColor = color;
    }

    @Override
    public void setFocused(boolean isFocused) {
        if (isFocused && !isFocused()) {
            pseudoCursorCounter = 0;
        }
        super.setFocused(isFocused);
    }

    @Override
    public void setBordered(boolean enableBackgroundDrawing) {
        super.setBordered(enableBackgroundDrawing);
        pseudoEnableBackgroundDrawing = enableBackgroundDrawing;
    }

    @Override
    public void setMaxLength(int length) {
        super.setMaxLength(length);
        pseudoMaxStringLength = length;
    }

    @Override
    public void tick() {
        super.tick();
        pseudoCursorCounter++;
    }

    @Override
    public void setHighlightPos(int position) {
        super.setHighlightPos(position);
        int i = getValue().length();
        pseudoSelectionEnd = Mth.clamp(position, 0, i);
        if (fontRenderer != null) {
            if (pseudoLineScrollOffset > i) {
                pseudoLineScrollOffset = i;
            }

            int j = getInnerWidth();
            String s = fontRenderer.plainSubstrByWidth(getValue().substring(this.pseudoLineScrollOffset), j, false);
            int k = s.length() + pseudoLineScrollOffset;
            if (pseudoSelectionEnd == pseudoLineScrollOffset) {
                pseudoLineScrollOffset -= fontRenderer.plainSubstrByWidth(getValue(), j, true).length();
            }

            if (pseudoSelectionEnd > k) {
                pseudoLineScrollOffset += pseudoSelectionEnd - k;
            } else if (pseudoSelectionEnd <= pseudoLineScrollOffset) {
                pseudoLineScrollOffset -= pseudoLineScrollOffset - pseudoSelectionEnd;
            }

            pseudoLineScrollOffset = Mth.clamp(pseudoLineScrollOffset, 0, i);
        }
    }

    public void setLabel(Component label) {
        this.label = label;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    private void drawSelectionBox(int startX, int startY, int endX, int endY) {
        if (startX < endX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        if (startY < endY) {
            int j = startY;
            startY = endY;
            endY = j;
        }

        if (endX > getX() + getWidth()) {
            endX = getX() + getWidth();
        }

        if (startX > getX() + getWidth()) {
            startX = getX() + getWidth();
        }

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShaderColor(0.0F, 0.0F, 255.0F, 255.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        bufferbuilder.vertex((double) startX, (double) endY, 0.0D).endVertex();
        bufferbuilder.vertex((double) endX, (double) endY, 0.0D).endVertex();
        bufferbuilder.vertex((double) endX, (double) startY, 0.0D).endVertex();
        bufferbuilder.vertex((double) startX, (double) startY, 0.0D).endVertex();
        tesselator.end();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

}
