package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GameState implements IGameState{

    private final double currentDay;
    private final double moraleValue;
    private final double supplyValue;
    private final ArrayList<QuestInfo<Point2D>> questList;
    private ArrayList<QuestInfo<Double>> completedQuests;

    public GameState(double currentDayIn,double moraleValueIn, double supplyValueIn, ArrayList<QuestInfo<Point2D>> questListIn){
        currentDay=currentDayIn;
        moraleValue=moraleValueIn;
        supplyValue=supplyValueIn;
        questList=questListIn;
        completedQuests=new ArrayList<>();
    }

    @Override
    public double getCurrentDay() {
        return currentDay;
    }

    @Override
    public boolean isSiegeBegun() {
        return currentDay >= 3;
    }

    @Override
    public ArrayList<QuestInfo<Point2D>> getQuests() {
        return questList;
    }

    @Override
    public ArrayList<QuestInfo<Double>> getCompletedQuests() {
        return completedQuests;
    }

    @Override
    public void addCompletedQuest(QuestInfo<Double> completedQuest) {
        completedQuests.add(completedQuest);
    }

    @Override
    public double getMoraleValue() {
        return moraleValue;
    }

    @Override
    public double getSupplyValue() {
        return supplyValue;
    }

    @Override
    public String getGameState() {
        return "It's the "+(int) currentDay+" day of the Game.\nThe available quests are:\n"+enumerateQuestNames()+"\n"+enumerateCompletedQuests();
    }

    private String enumerateQuestNames(){
        String res="";

        if(questList.size()==0){
            return "None";
        } else {
            for (var quest : questList) {
                res = res + quest.getQuest().getQuestName() + "; ";
            }
        }

        return res;
    }

    private String enumerateCompletedQuests(){
        String res="";

        if(completedQuests.size()==0){
            return "";
        } else {
            for (var quest : completedQuests) {
                res = res + quest.getQuest().getQuestName() + " was completed\n";
            }
        }

        return res;
    }
}
