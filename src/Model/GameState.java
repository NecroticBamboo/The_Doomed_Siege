package Model;

import java.util.ArrayList;

public class GameState implements IGameState{

    private final double currentDay;
    private final double moraleValue;
    private final double supplyValue;
    private final ArrayList<IQuest> questList;

    public GameState(double currentDayIn,double moraleValueIn, double supplyValueIn, ArrayList<IQuest> questListIn){
        currentDay=currentDayIn;
        moraleValue=moraleValueIn;
        supplyValue=supplyValueIn;
        questList=questListIn;
    }

    @Override
    public double getCurrentDay() {
        return currentDay;
    }

    @Override
    public boolean isSiegeBegun() {
        return currentDay == 4;
    }

    @Override
    public ArrayList<IQuest> getQuests() {
        return questList;
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
        return null;
    }
}
