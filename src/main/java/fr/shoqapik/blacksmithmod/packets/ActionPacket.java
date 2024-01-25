package fr.shoqapik.blacksmithmod.packets;

import fr.shoqapik.blacksmithmod.BlackSmithMod;
import fr.shoqapik.blacksmithmod.client.BlackSmithModClient;
import fr.shoqapik.blacksmithmod.quests.QuestAnswer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ActionPacket {

    public UUID entityUuid;
    public String actionType;

    public ActionPacket(UUID entityUuid, String actionType) {
        this.entityUuid = entityUuid;
        this.actionType = actionType;
    }

    public static void handle(ActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                BlackSmithMod.handleActionPacket(msg, ctx)
        );
        ctx.get().setPacketHandled(true);
    }


    public static ActionPacket decode(FriendlyByteBuf packetBuffer) {
        UUID entityUUID = packetBuffer.readUUID();
        String actionType = packetBuffer.readUtf();
        return new ActionPacket(entityUUID, actionType);
    }

    public static void encode(ActionPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUUID(msg.entityUuid);
        packetBuffer.writeUtf(msg.actionType);
    }


}
