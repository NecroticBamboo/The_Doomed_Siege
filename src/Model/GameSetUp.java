package Model;

import Data.utils.ConnectionInfo;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class GameSetUp implements IGameSetUp{

    private final int gameId;
    private final String gameName;
    private final String mapUrl;
    private final double difficulty;
    private final int numberOfDays;
    private final int numberOfQuestsPerDay;
    private final int questSelectCountPerDay;
    private final double initialMorale;
    private final double initialSupply;

    private final Map<Integer,ArrayList<QuestIdentifier>> availableQuests;


    public GameSetUp(int gameId, String gameName, String mapUrl, double difficulty, int numberOfDays, int numberOfQuestsPerDay, int questSelectCountPerDay, double initialMorale, double initialSupply, Map<Integer,ArrayList<QuestIdentifier>> availableQuests) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.mapUrl = mapUrl;
        this.difficulty = difficulty;
        this.numberOfDays = numberOfDays;
        this.numberOfQuestsPerDay = numberOfQuestsPerDay;
        this.questSelectCountPerDay = questSelectCountPerDay;
        this.initialMorale = initialMorale;
        this.initialSupply = initialSupply;

        this.availableQuests=availableQuests;
    }

    @Override
    public String getMapUrl() {
        return mapUrl;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    @Override
    public double getDifficulty() {
        return difficulty;
    }

    @Override
    public int getNumberOfDays() {
        return numberOfDays;
    }

    @Override
    public int getNumberOfQuestsPerDay() {
        return numberOfQuestsPerDay;
    }

    @Override
    public int getQuestSelectCountPerDay() {
        return questSelectCountPerDay;
    }

    @Override
    public Map<Integer,ArrayList<QuestIdentifier>> getAvailableQuests(){
        return availableQuests;
    }

    @Override
    public double getInitialMorale() {
        return initialMorale;
    }

    @Override
    public double getInitialSupply() {
        return initialSupply;
    }
}
