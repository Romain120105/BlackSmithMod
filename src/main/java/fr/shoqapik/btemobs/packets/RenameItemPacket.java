package fr.shoqapik.btemobs.packets;

import fr.shoqapik.btemobs.BteMobsMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RenameItemPacket {
    private final String name;

    public RenameItemPacket(String pName) {
        this.name = pName;
    }

    public RenameItemPacket(FriendlyByteBuf pBuffer) {
        this.name = pBuffer.readUtf();
    }

    public static RenameItemPacket decode(FriendlyByteBuf pBuffer) {
        return new RenameItemPacket(pBuffer.readUtf());
    }

    public static void encode(RenameItemPacket msg, FriendlyByteBuf pBuffer) {
        pBuffer.writeUtf(msg.name);
    }

    public static void handle(RenameItemPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                BteMobsMod.handleRenameItemPacket(msg, ctx)
        );
        ctx.get().setPacketHandled(true);
    }

    public String getName() {
        return this.name;
    }
}