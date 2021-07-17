package Model;

import Data.QuestType;

import java.util.ArrayList;
import java.util.Map;

public interface IGameSetUp {

    String getGameName();

    double getDifficulty();

    String getMapUrl();

    int getNumberOfDays();

    int getNumberOfQuestsPerDay();
    int getQuestSelectCountPerDay();

    Map<QuestType,ArrayList<QuestIdentifier>> getAvailableQuests();

    double getInitialMorale();
    double getInitialSupply();
}
