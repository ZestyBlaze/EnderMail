package dev.zestyblaze.endermail;

import dev.zestyblaze.endermail.gui.LockerScreen;
import dev.zestyblaze.endermail.gui.PackageScreen;
import dev.zestyblaze.endermail.registry.EnderMailContainers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class EnderMailClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(EnderMailContainers.PACKAGE_CONTAINER, PackageScreen::new);
        MenuScreens.register(EnderMailContainers.LOCKER_CONTAINER, LockerScreen::new);
    }
}
