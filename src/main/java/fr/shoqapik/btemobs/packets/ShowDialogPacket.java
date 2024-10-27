package fr.shoqapik.btemobs.packets;

import fr.shoqapik.btemobs.client.BteMobsModClient;
import fr.shoqapik.btemobs.quests.QuestAnswer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ShowDialogPacket {
    public int entityId;
    public String entityName;
    public List<String> dialogs;
    public List<QuestAnswer> answers;

    public ShowDialogPacket(int entityId, String entityName, List<String> dialogs, List<QuestAnswer> answers) {
        this.entityId = entityId;
        this.entityName = entityName;
        this.dialogs = dialogs;
        this.answers = answers;
    }

    public static void handle(ShowDialogPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> BteMobsModClient.handleDialogPacket(msg, ctx))
        );
        ctx.get().setPacketHandled(true);
    }


    public static ShowDialogPacket decode(FriendlyByteBuf packetBuffer) {
        int entityId = packetBuffer.readInt();
        String entityName =packetBuffer.readUtf();
        int dialogNumber = packetBuffer.readInt();
        List<String> dialogs =new ArrayList<>();
        for(int i =0; i < dialogNumber; i++){
            dialogs.add(packetBuffer.readUtf());
        }

        int answersNumber = packetBuffer.readInt();
        List<QuestAnswer> answers =new ArrayList<>();
        for(int i =0; i < answersNumber; i++){
            answers.add(new QuestAnswer(packetBuffer.readUtf(), packetBuffer.readUtf()));
        }
        return new ShowDialogPacket(entityId, entityName, dialogs, answers);
    }

    public static void encode(ShowDialogPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.entityId);
        packetBuffer.writeUtf(msg.entityName);
        packetBuffer.writeInt(msg.dialogs.size());
        for (String s: msg.dialogs) {
            packetBuffer.writeUtf(s);
        }
        packetBuffer.writeInt(msg.answers.size());
        for (QuestAnswer answer: msg.answers) {
            packetBuffer.writeUtf(answer.getFormattedAwnser());
            packetBuffer.writeUtf(answer.getAction());
        }
    }

}
