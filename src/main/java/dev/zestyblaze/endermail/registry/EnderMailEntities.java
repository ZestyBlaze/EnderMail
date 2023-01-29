package dev.zestyblaze.endermail.registry;

import dev.zestyblaze.endermail.EnderMail;
import dev.zestyblaze.endermail.entity.EnderMailmanEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EnderMailEntities {
    public static final EntityType<EnderMailmanEntity> ENDER_MAILMAN = FabricEntityTypeBuilder.<EnderMailmanEntity>create(MobCategory.MONSTER, EnderMailmanEntity::new).trackRangeBlocks(80).trackedUpdateRate(3).forceTrackedVelocityUpdates(true).dimensions(EntityDimensions.scalable(0.6f, 2.9f)).build();

    public static void register() {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(EnderMail.MODID, EnderMailmanEntity.NAME), ENDER_MAILMAN);
    }
}
