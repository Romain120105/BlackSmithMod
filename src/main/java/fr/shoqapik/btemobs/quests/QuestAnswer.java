package fr.shoqapik.btemobs.quests;

public class QuestAnswer {

    private String formattedText;
    private String action;

    public QuestAnswer(String formattedText, String action) {
        this.formattedText = formattedText;
        this.action = action;
    }

    public String getFormattedAwnser() {
        return formattedText;
    }

    public String getAction() {
        return action;
    }
}
