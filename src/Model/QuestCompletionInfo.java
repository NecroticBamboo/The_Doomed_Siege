package Model;

public class QuestCompletionInfo {

    private final double questModifier;
    private final IQuest quest;

    public QuestCompletionInfo(double questModifierIn, IQuest questIn){
        questModifier=questModifierIn;
        quest=questIn;
    }

    public double getQuestModifier(){
        return questModifier;
    }

    public IQuest getQuest(){
        return quest;
    }
}
