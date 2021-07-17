package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public interface IGameState {

    double getCurrentDay();
//    void setCurrentDay(int currentDay);

    boolean isSiegeBegun();

    ArrayList<QuestInfo<Point2D>> getQuests();

    ArrayList<QuestInfo<Double>> getCompletedQuests();
    void addCompletedQuest(QuestInfo<Double> completedQuest);

    double getMoraleValue();
    double getSupplyValue();

    String getGameState();

}
