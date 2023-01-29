package dev.zestyblaze.endermail.registry;

import dev.zestyblaze.endermail.EnderMail;
import dev.zestyblaze.endermail.block.LockerBlock;
import dev.zestyblaze.endermail.block.PackageBlock;
import dev.zestyblaze.endermail.item.PackageControllerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class EnderMailItems {
    public static final Item PACKAGE = new BlockItem(EnderMailBlocks.PACKAGE, new Item.Properties());
    public static final Item LOCKER = new BlockItem(EnderMailBlocks.LOCKER, new Item.Properties());
    public static final Item PACKAGE_CONTROLLER = new PackageControllerItem();
    public static final Item PACKING_TAPE = new Item(new Item.Properties());
    public static final Item STAMP = new Item(new Item.Properties());

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EnderMail.MODID, PackageBlock.NAME), PACKAGE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EnderMail.MODID, LockerBlock.NAME), LOCKER);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EnderMail.MODID, PackageControllerItem.NAME), PACKAGE_CONTROLLER);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EnderMail.MODID, "packing_tape"), PACKING_TAPE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EnderMail.MODID, "stamp"), STAMP);
    }
}
