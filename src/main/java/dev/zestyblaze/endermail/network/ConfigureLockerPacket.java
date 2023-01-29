package dev.zestyblaze.endermail.network;

import dev.zestyblaze.endermail.block.LockerBlock;
import dev.zestyblaze.endermail.block.entity.LockerBlockEntity;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ConfigureLockerPacket implements C2SPacket {
    private int lockerX;
    private int lockerY;
    private int lockerZ;
    private String lockerID;

    public ConfigureLockerPacket() {
    }

    public ConfigureLockerPacket(BlockPos lockerPos, String lockerID) {
        this.lockerX = lockerPos.getX();
        this.lockerY = lockerPos.getY();
        this.lockerZ = lockerPos.getZ();
        this.lockerID = lockerID;
    }

    public ConfigureLockerPacket(FriendlyByteBuf buf) {
        lockerX = buf.readInt();
        lockerY = buf.readInt();
        lockerZ = buf.readInt();

        lockerID = buf.readUtf(LockerBlock.MAX_ID_LENGTH);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(lockerX);
        buf.writeInt(lockerY);
        buf.writeInt(lockerZ);

        buf.writeUtf(lockerID);
    }

    @Override
    public void handle(MinecraftServer minecraftServer, ServerPlayer serverPlayer, ServerGamePacketListenerImpl serverGamePacketListener, PacketSender packetSender, SimpleChannel simpleChannel) {
        BlockEntity blockEntity = serverPlayer.getLevel().getBlockEntity(new BlockPos(lockerX, lockerY, lockerZ));
        if(blockEntity instanceof LockerBlockEntity lockerBlockEntity) {
            lockerBlockEntity.setLockerID(lockerID);
        }
    }
}
