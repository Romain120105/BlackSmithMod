package fr.shoqapik.btemobs.packets;

import fr.shoqapik.btemobs.client.BteMobsModClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleCraftButton {

    public final boolean active;

    public ToggleCraftButton(boolean active) {
        this.active = active;
    }

    public static void handle(ToggleCraftButton msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                BteMobsModClient.handleToggleCraftButtonPacket(msg, ctx)
        );
        ctx.get().setPacketHandled(true);
    }


    public static ToggleCraftButton decode(FriendlyByteBuf packetBuffer) {
        return new ToggleCraftButton(packetBuffer.readBoolean());
    }

    public static void encode(ToggleCraftButton msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeBoolean(msg.active);
    }
}
