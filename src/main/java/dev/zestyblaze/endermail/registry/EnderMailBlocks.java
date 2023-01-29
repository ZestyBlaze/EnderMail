package dev.zestyblaze.endermail.registry;

import dev.zestyblaze.endermail.EnderMail;
import dev.zestyblaze.endermail.block.LockerBlock;
import dev.zestyblaze.endermail.block.PackageBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class EnderMailBlocks {
    public static final PackageBlock PACKAGE = new PackageBlock();
    public static final LockerBlock LOCKER = new LockerBlock();

    public static void register() {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(EnderMail.MODID, PackageBlock.NAME), PACKAGE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(EnderMail.MODID, LockerBlock.NAME), LOCKER);
    }
}
