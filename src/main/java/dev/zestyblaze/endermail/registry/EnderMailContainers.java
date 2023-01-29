package dev.zestyblaze.endermail.registry;

import dev.zestyblaze.endermail.EnderMail;
import dev.zestyblaze.endermail.gui.container.LockerMenu;
import dev.zestyblaze.endermail.gui.container.PackageMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class EnderMailContainers {
    public static final MenuType<PackageMenu> PACKAGE_CONTAINER = new MenuType<>(PackageMenu::new);
    public static final MenuType<LockerMenu> LOCKER_CONTAINER = new ExtendedScreenHandlerType<>(LockerMenu::new);

    public static void register() {
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(EnderMail.MODID, PackageMenu.NAME), PACKAGE_CONTAINER);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(EnderMail.MODID, LockerMenu.NAME), LOCKER_CONTAINER);
    }
}
