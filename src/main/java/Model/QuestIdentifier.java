package Model;

import Data.QuestType;

public class QuestIdentifier{

    private final int questId;
    private final String questName;
    private final QuestType questType;

    public QuestIdentifier(int questIdIn,String questNameIn, QuestType questTypeIn){
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

    public QuestType getQuestType() {
        return questType;
    }
}
