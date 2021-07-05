package Model;

import Data.QuestType;

public interface IQuest {

    int getQuestId();

    String getQuestName();
    void setQuestName(String questNameIn);

    String getQuestDescription();
    void setQuestDescription(String questDescriptionIn);

    QuestType getQuestType();
    void setQuestType(QuestType questTypeIn);

    boolean isUsed();
    void setUsed(boolean state);

    double getMoraleResult(QuestOutcome outcome);
    double getSupplyResult(QuestOutcome outcome);

}
