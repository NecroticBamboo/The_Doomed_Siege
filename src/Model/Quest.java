package Model;

import Data.QuestType;

public class Quest implements IQuest{

    private final int questId;
    private String questName;
    private String questDescription;
    private QuestType questType;
    private boolean isUsed;

    private final int moraleBadResult;
    private final int supplyBadResult;
    private final int moraleNormalResult;
    private final int supplyNormalResult;
    private final int moraleGoodResult;
    private final int supplyGoodResult;
    private final int moraleDefaultResult;
    private final int supplyDefaultResult;

    public Quest(int questIdIn,
                 String questNameIn,
                 String questDescriptionIn,
                 QuestType questTypeIn,
                 boolean isUsedIn,
                 int moraleBadResultIn,
                 int supplyBadResultIn,
                 int moraleNormalResultIn,
                 int supplyNormalResultIn,
                 int moraleGoodResultIn,
                 int supplyGoodResultIn,
                 int moraleDefaultResultIn,
                 int supplyDefaultResultIn){

        questId=questIdIn;
        questName=questNameIn;
        questDescription=questDescriptionIn;
        questType=questTypeIn;
        isUsed=isUsedIn;

        moraleBadResult=moraleBadResultIn;
        supplyBadResult=supplyBadResultIn;
        moraleNormalResult=moraleNormalResultIn;
        supplyNormalResult=supplyNormalResultIn;
        moraleGoodResult=moraleGoodResultIn;
        supplyGoodResult=supplyGoodResultIn;
        moraleDefaultResult=moraleDefaultResultIn;
        supplyDefaultResult=supplyDefaultResultIn;
    }

    @Override
    public int getQuestId() {
        return questId;
    }

    @Override
    public String getQuestName() {
        return questName;
    }

    @Override
    public void setQuestName(String questNameIn) {
        questName=questNameIn;
    }

    @Override
    public String getQuestDescription() {
        return questDescription;
    }

    @Override
    public void setQuestDescription(String questDescriptionIn) {
        questDescription=questDescriptionIn;
    }

    @Override
    public QuestType getQuestType() {
        return questType;
    }

    @Override
    public void setQuestType(QuestType questTypeIn) {
        questType=questTypeIn;
    }

    @Override
    public boolean isUsed() {
        return isUsed;
    }

    @Override
    public void setUsed(boolean state) {
        isUsed=state;
    }

    @Override
    public double getMoraleResult(QuestOutcome outcome) {

        switch (outcome){
            case BAD:
                return moraleBadResult;
            case NORMAL:
                return moraleNormalResult;
            case GOOD:
                return moraleGoodResult;
            default:
                return moraleDefaultResult;
        }
    }

    @Override
    public double getSupplyResult(QuestOutcome outcome) {
        switch (outcome){
            case BAD:
                return supplyBadResult;
            case NORMAL:
                return supplyNormalResult;
            case GOOD:
                return supplyGoodResult;
            default:
                return supplyDefaultResult;
        }
    }


}
