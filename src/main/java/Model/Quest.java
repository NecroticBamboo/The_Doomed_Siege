package Model;

import Data.QuestType;

public class Quest implements IQuest{

    private final int questId;
    private String questName;
    private String questDescription;
    private QuestType questType;
    private boolean isUsed;

    private final double moraleBadResult;
    private final double supplyBadResult;
    private final double moraleNormalResult;
    private final double supplyNormalResult;
    private final double moraleGoodResult;
    private final double supplyGoodResult;
    private final double moraleDefaultResult;
    private final double supplyDefaultResult;

    public Quest(int questIdIn,
                 String questNameIn,
                 String questDescriptionIn,
                 QuestType questTypeIn,
                 boolean isUsedIn,
                 double moraleBadResultIn,
                 double supplyBadResultIn,
                 double moraleNormalResultIn,
                 double supplyNormalResultIn,
                 double moraleGoodResultIn,
                 double supplyGoodResultIn,
                 double moraleDefaultResultIn,
                 double supplyDefaultResultIn){

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
    public double getMoraleResult(QuestOutcome outcome) { //???? returns int as doubles

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
    public double getSupplyResult(QuestOutcome outcome) { //???? returns int as doubles
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
