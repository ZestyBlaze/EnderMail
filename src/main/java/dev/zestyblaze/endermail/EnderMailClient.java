package dev.zestyblaze.endermail;

import dev.zestyblaze.endermail.gui.LockerScreen;
import dev.zestyblaze.endermail.gui.PackageScreen;
import dev.zestyblaze.endermail.item.PackageControllerItem;
import dev.zestyblaze.endermail.registry.EnderMailContainers;
import dev.zestyblaze.endermail.registry.EnderMailItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EnderMailClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(EnderMailContainers.PACKAGE_CONTAINER, PackageScreen::new);
        MenuScreens.register(EnderMailContainers.LOCKER_CONTAINER, LockerScreen::new);

        ItemProperties.register(EnderMailItems.PACKAGE_CONTROLLER, new ResourceLocation("state"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
                if (stack.getItem() == EnderMailItems.PACKAGE_CONTROLLER) {
                    PackageControllerItem packageController = (PackageControllerItem) stack.getItem();
                    return packageController.getState(stack).getID();
                }
                return 0F;
            }
        });
    }
}
