package dev.zestyblaze.endermail.registry;

import dev.zestyblaze.endermail.EnderMail;
import dev.zestyblaze.endermail.block.LockerBlock;
import dev.zestyblaze.endermail.block.PackageBlock;
import dev.zestyblaze.endermail.block.entity.LockerBlockEntity;
import dev.zestyblaze.endermail.block.entity.PackageBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EnderMailBlockEntities {
    public static final BlockEntityType<PackageBlockEntity> PACKAGE = FabricBlockEntityTypeBuilder.create(PackageBlockEntity::new, EnderMailBlocks.PACKAGE).build();
    public static final BlockEntityType<LockerBlockEntity> LOCKER = FabricBlockEntityTypeBuilder.create(LockerBlockEntity::new, EnderMailBlocks.LOCKER).build();

    public static void register() {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(EnderMail.MODID, PackageBlock.NAME), PACKAGE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(EnderMail.MODID, LockerBlock.NAME), LOCKER);
    }
}
