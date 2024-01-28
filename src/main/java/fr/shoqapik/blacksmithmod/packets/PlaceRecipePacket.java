package fr.shoqapik.blacksmithmod.packets;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PlaceRecipePacket {

    public String recipe;

    public PlaceRecipePacket(String recipe) {
        this.recipe = recipe;
    }

    public static void handle(PlaceRecipePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                BlackSmithMod.handlePlaceRecipePacket(msg, ctx)
        );
        ctx.get().setPacketHandled(true);
    }


    public static PlaceRecipePacket decode(FriendlyByteBuf packetBuffer) {
        String recipe = packetBuffer.readUtf();
        return new PlaceRecipePacket(recipe);
    }

    public static void encode(PlaceRecipePacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUtf(msg.recipe);
    }



}
