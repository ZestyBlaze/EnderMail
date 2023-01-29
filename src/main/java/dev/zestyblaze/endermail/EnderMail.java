package dev.zestyblaze.endermail;

import dev.zestyblaze.endermail.config.ConfigHandler;
import dev.zestyblaze.endermail.network.ConfigureLockerPacket;
import dev.zestyblaze.endermail.network.StampPackagePacket;
import dev.zestyblaze.endermail.registry.*;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderMail implements ModInitializer {

    public static final String MODID = "endermail";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static SimpleChannel network;

    @Override
    public void onInitialize() {
        EnderMailBlocks.register();
        EnderMailBlockEntities.register();
        EnderMailContainers.register();
        EnderMailEntities.register();
        EnderMailItems.register();

        preInit();
        buildCreativeTabContents();

        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, ConfigHandler.GENERAL_SPEC);
        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
    }

    private void preInit() {
        network = new SimpleChannel(new ResourceLocation(EnderMail.MODID, EnderMail.MODID));
        network.registerC2SPacket(StampPackagePacket.class, 0, StampPackagePacket::new);
        network.registerC2SPacket(ConfigureLockerPacket.class, 1, ConfigureLockerPacket::new);
    }

    private void buildCreativeTabContents() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(event -> {
            event.accept(EnderMailItems.PACKAGE_CONTROLLER);
            event.accept(EnderMailItems.STAMP);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(event -> {
            event.accept(EnderMailItems.PACKING_TAPE);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(event -> {
            event.accept(EnderMailItems.PACKAGE);
            event.accept(EnderMailItems.LOCKER);
        });
    }
}
