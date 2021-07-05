package Model;

import java.util.ArrayList;
import java.util.Map;

public interface IGameSetUp {

    String getGameName();

    double getDifficulty();

    String getMapUrl();

    int getNumberOfDays();

    int getNumberOfQuestsPerDay();
    int getQuestSelectCountPerDay();

    Map<Integer,ArrayList<QuestIdentifier>> getAvailableQuests();

    double getInitialMorale();
    double getInitialSupply();
}
