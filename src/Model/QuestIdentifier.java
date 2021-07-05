package Model;

public class QuestIdentifier{

    private final int questId;
    private final String questName;
    private final int questType;

    public QuestIdentifier(int questIdIn,String questNameIn, int questTypeIn){
        questId=questIdIn;
        questName=questNameIn;
        questType=questTypeIn;
    }

    public int getQuestId() {
        return questId;
    }

    public String getQuestName() {
        return questName;
    }

    public int getQuestType() {
        return questType;
    }
}
