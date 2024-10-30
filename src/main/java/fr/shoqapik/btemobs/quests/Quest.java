package fr.shoqapik.btemobs.quests;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class Quest {

    private String entityId;
    private Quest.Type type;
    private List<String> dialogs;

    private List<QuestAnswer> answers;

    private transient ResourceLocation entityIdLocation;

    public ResourceLocation getEntityId() {
        if(entityIdLocation ==null){
            entityIdLocation = ResourceLocation.tryParse(entityId);

        }
        return entityIdLocation;
    }

    public Quest.Type getType() {
        return type;
    }

    public List<String> getDialogs() {
        return dialogs;
    }

    public List<QuestAnswer> getAnswers() {
        return answers;
    }

    public enum Type {
        PRESENTATION,
        TASKING
    }
}