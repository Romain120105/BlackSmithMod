package fr.shoqapik.blacksmithmod.packets;

import fr.shoqapik.blacksmithmod.client.BlackSmithModClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlackSmithShowDialogPacket {

    public BlackSmithShowDialogPacket() {
    }

    public static void handle(BlackSmithShowDialogPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> BlackSmithModClient.handleDialogPacket(msg, ctx))
        );
        ctx.get().setPacketHandled(true);
    }


    public static BlackSmithShowDialogPacket decode(FriendlyByteBuf packetBuffer) {

        return new BlackSmithShowDialogPacket();
    }

    public static void encode(BlackSmithShowDialogPacket msg, FriendlyByteBuf packetBuffer) {

    }

}
