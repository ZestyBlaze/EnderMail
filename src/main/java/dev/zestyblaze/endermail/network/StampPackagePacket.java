package dev.zestyblaze.endermail.network;

import dev.zestyblaze.endermail.block.LockerBlock;
import dev.zestyblaze.endermail.block.PackageBlock;
import dev.zestyblaze.endermail.registry.EnderMailItems;
import dev.zestyblaze.endermail.util.ItemUtils;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;

public class StampPackagePacket implements C2SPacket {
    private int packageX;
    private int packageY;
    private int packageZ;

    private int deliveryX;
    private int deliveryY;
    private int deliveryZ;

    private String lockerID;

    private boolean hasDeliveryPos;

    public StampPackagePacket() {
    }

    public StampPackagePacket(BlockPos packagePos, BlockPos deliveryPos, String lockerID, boolean hasDeliveryPos) {
        this.packageX = packagePos.getX();
        this.packageY = packagePos.getY();
        this.packageZ = packagePos.getZ();

        this.deliveryX = deliveryPos.getX();
        this.deliveryY = deliveryPos.getY();
        this.deliveryZ = deliveryPos.getZ();

        this.lockerID = lockerID;

        this.hasDeliveryPos = hasDeliveryPos;
    }

    public StampPackagePacket(FriendlyByteBuf buf) {
        packageX = buf.readInt();
        packageY = buf.readInt();
        packageZ = buf.readInt();

        deliveryX = buf.readInt();
        deliveryY = buf.readInt();
        deliveryZ = buf.readInt();

        lockerID = buf.readUtf(LockerBlock.MAX_ID_LENGTH);

        hasDeliveryPos = buf.readBoolean();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(packageX);
        buf.writeInt(packageY);
        buf.writeInt(packageZ);

        buf.writeInt(deliveryX);
        buf.writeInt(deliveryY);
        buf.writeInt(deliveryZ);

        buf.writeUtf(lockerID);

        buf.writeBoolean(hasDeliveryPos);
    }

    @Override
    public void handle(MinecraftServer minecraftServer, ServerPlayer serverPlayer, ServerGamePacketListenerImpl serverGamePacketListener, PacketSender packetSender, SimpleChannel simpleChannel) {
        PackageBlock.stampPackage(serverPlayer.getLevel(), new BlockPos(packageX, packageY, packageZ), new BlockPos(deliveryX, deliveryY, deliveryZ), lockerID, hasDeliveryPos);
        ItemStack stampStack = ItemUtils.getHeldItem(serverPlayer, EnderMailItems.STAMP);
        if (stampStack != null && !serverPlayer.isCreative()) {
            stampStack.setCount(stampStack.getCount() - 1);
        }
    }
}
