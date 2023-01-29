package dev.zestyblaze.endermail.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class ScreenWrapper {

    public static void openStampScreen(Level level, Player player, BlockPos packagePos) {
        Minecraft.getInstance().setScreen(new StampScreen(level, player, packagePos));
    }

}
