package Model;

import java.util.ArrayList;

public interface IGameState {

    double getCurrentDay();
//    void setCurrentDay(int currentDay);

    boolean isSiegeBegun();

    ArrayList<IQuest> getQuests();

    double getMoraleValue();
    double getSupplyValue();

    String getGameState();

}
