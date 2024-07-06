package fr.shoqapik.blacksmithmod.packets;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CraftItemPacket {

    public String recipe;

    public CraftItemPacket(String recipe) {
        this.recipe = recipe;
    }

    public static void handle(CraftItemPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                BlackSmithMod.handleCraftItemPacket(msg, ctx)
        );
        ctx.get().setPacketHandled(true);
    }


    public static CraftItemPacket decode(FriendlyByteBuf packetBuffer) {
        String recipe = packetBuffer.readUtf();
        return new CraftItemPacket(recipe);
    }

    public static void encode(CraftItemPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUtf(msg.recipe);
    }
}
