package Data;

import Data.DI.IServiceLocator;
import Data.utils.ConnectionInfo;
import Data.utils.IDatabaseManager;
import Model.*;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database implements IDatabase{

    private final IDatabaseManager databaseManager;

    public Database(IServiceLocator serviceLocatorIn){
        databaseManager = serviceLocatorIn.getService("IDatabaseManager");;
    }

    @Override
    public Quest getQuestById(int questId) {

        ConnectionInfo ci = databaseManager.acquire();
        try {
            Connection con = ci.getConnection();
            if (con == null) return null;

            String condition = "select QuestName, QuestDescription, QuestType, IsUsed, MoraleBadResult, SupplyBadResult, MoraleNormalResult, SupplyNormalResult, MoraleGoodResult, SupplyGoodResult, MoraleDefaultResult, SupplyDefaultResult from Quests where QuestId=?";
            PreparedStatement st=con.prepareStatement(condition);
            st.setInt(1,questId);

            ResultSet resultSet = st.executeQuery();
            String questName = resultSet.getString("QuestName");
            String questDescription = resultSet.getString("QuestDescription");
            QuestType questType = QuestType.fromInt(resultSet.getInt("QuestType")-1);
            boolean isUsed = resultSet.getBoolean("IsUsed");

            int moraleBadResult=resultSet.getInt("MoraleBadResult");
            int supplyBadResult=resultSet.getInt("SupplyBadResult");
            int moraleNormalResult=resultSet.getInt("MoraleNormalResult");
            int supplyNormalResult=resultSet.getInt("SupplyNormalResult");
            int moraleGoodResult=resultSet.getInt("MoraleGoodResult");
            int supplyGoodResult=resultSet.getInt("SupplyGoodResult");
            int moraleDefaultResult=resultSet.getInt("MoraleDefaultResult");
            int supplyDefaultResult=resultSet.getInt("SupplyDefaultResult");

            Quest quest = new Quest(questId,questName,questDescription,questType,isUsed,moraleBadResult,supplyBadResult,moraleNormalResult,supplyNormalResult,moraleGoodResult,supplyGoodResult,moraleDefaultResult,supplyDefaultResult);
//            System.out.println(quest);
            return quest;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Quest getQuestByName(String questName) {
        ConnectionInfo ci = databaseManager.acquire();
        try {
            Connection con = ci.getConnection();
            if (con == null) return null;

            String condition = "select QuestId, QuestDescription, QuestType, IsUsed, MoraleBadResult, SupplyBadResult, MoraleNormalResult, SupplyNormalResult, MoraleGoodResult, SupplyGoodResult, MoraleDefaultResult, SupplyDefaultResult from Quests where QuestName=?";
            PreparedStatement st=con.prepareStatement(condition);
            st.setString(1,questName);

            ResultSet resultSet = st.executeQuery();
            int questId = resultSet.getInt("QuestId");
            String questDescription = resultSet.getString("QuestDescription");
            QuestType questType = QuestType.fromInt(resultSet.getInt("QuestType")-1);
            boolean isUsed = resultSet.getBoolean("IsUsed");

            int moraleBadResult=resultSet.getInt("MoraleBadResult");
            int supplyBadResult=resultSet.getInt("SupplyBadResult");
            int moraleNormalResult=resultSet.getInt("MoraleNormalResult");
            int supplyNormalResult=resultSet.getInt("SupplyNormalResult");
            int moraleGoodResult=resultSet.getInt("MoraleGoodResult");
            int supplyGoodResult=resultSet.getInt("SupplyGoodResult");
            int moraleDefaultResult=resultSet.getInt("MoraleDefaultResult");
            int supplyDefaultResult=resultSet.getInt("SupplyDefaultResult");

            Quest quest = new Quest(questId,questName,questDescription,questType,isUsed,moraleBadResult,supplyBadResult,moraleNormalResult,supplyNormalResult,moraleGoodResult,supplyGoodResult,moraleDefaultResult,supplyDefaultResult);
//            System.out.println(quest);
            return quest;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<IQuest> getQuestsByType(QuestType questType) {
        ConnectionInfo ci = databaseManager.acquire();
        try {
            Connection con = ci.getConnection();
            if (con == null) return null;

            ArrayList<IQuest> questsList = new ArrayList<>();

            String condition = "select QuestId,QuestName, QuestDescription, IsUsed, MoraleBadResult, SupplyBadResult, MoraleNormalResult, SupplyNormalResult, MoraleGoodResult, SupplyGoodResult, MoraleDefaultResult, SupplyDefaultResult from Quests where QuestType=?";
            PreparedStatement st=con.prepareStatement(condition);
            st.setInt(1,questType.ordinal() + 1);

            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()){
                int questId = resultSet.getInt("QuestId");
                String questName = resultSet.getString("QuestName");
                String questDescription = resultSet.getString("QuestDescription");
                boolean isUsed = resultSet.getBoolean("IsUsed");

                int moraleBadResult=resultSet.getInt("MoraleBadResult");
                int supplyBadResult=resultSet.getInt("SupplyBadResult");
                int moraleNormalResult=resultSet.getInt("MoraleNormalResult");
                int supplyNormalResult=resultSet.getInt("SupplyNormalResult");
                int moraleGoodResult=resultSet.getInt("MoraleGoodResult");
                int supplyGoodResult=resultSet.getInt("SupplyGoodResult");
                int moraleDefaultResult=resultSet.getInt("MoraleDefaultResult");
                int supplyDefaultResult=resultSet.getInt("SupplyDefaultResult");

                Quest quest = new Quest(questId,questName,questDescription,questType,isUsed,moraleBadResult,supplyBadResult,moraleNormalResult,supplyNormalResult,moraleGoodResult,supplyGoodResult,moraleDefaultResult,supplyDefaultResult);
                questsList.add(quest);
            }
            return questsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public IGameSetUp getGameById(int gameId) throws SQLException{
        ConnectionInfo ci = databaseManager.acquire();

        Connection con = ci.getConnection();
        if (con == null) return null;

        String condition = "select Name, MapUrl, Difficulty, NumberOfDays, NumberOfQuestsPerDay, QuestSelectCountPerDay, InitialMorale, InitialSupply from Games where Id=?";
        PreparedStatement st = con.prepareStatement(condition);
        st.setInt(1,gameId);

        ResultSet resultSet = st.executeQuery();
        String gameName=resultSet.getString("Name");
        String mapUrl=resultSet.getString("MapUrl");
        double difficulty=resultSet.getDouble("Difficulty");
        int numberOfDays=resultSet.getInt("NumberOfDays");
        int numberOfQuestsPerDay=resultSet.getInt("NumberOfQuestsPerDay");
        int questSelectCountPerDay=resultSet.getInt("QuestSelectCountPerDay");
        double initialMorale=resultSet.getInt("InitialMorale")/100.0;
        double initialSupply=resultSet.getInt("initialSupply")/100.0;
        Map<Integer,ArrayList<QuestIdentifier>> availableQuests = getAvailableQuests(gameId);

        GameSetUp gameSetUp=new GameSetUp(gameId,gameName,mapUrl,difficulty,numberOfDays,numberOfQuestsPerDay,questSelectCountPerDay,initialMorale,initialSupply,availableQuests);
        return gameSetUp;
    }

    private Map<Integer,ArrayList<QuestIdentifier>> getAvailableQuests(int gameId) throws SQLException {
        // select a.QuestId, b.Name from AvailableQuests a join Quests b on a.QuestId = b.QuestId
        ConnectionInfo ci = databaseManager.acquire();

        Map<Integer,ArrayList<QuestIdentifier>> questMap = new HashMap<>();
        questMap.put(1,new ArrayList<>());
        questMap.put(2,new ArrayList<>());
        questMap.put(3,new ArrayList<>());

        Connection con = ci.getConnection();
        if (con == null) return null;

        String condition = "select a.QuestId, b.QuestName, b.QuestType from AvailableQuests a join Quests b on a.QuestId = b.QuestId where GameId=?";
        PreparedStatement st = con.prepareStatement(condition);
        st.setInt(1,gameId);

        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()){
            int questId = resultSet.getInt("QuestId");
            String questName = resultSet.getString("QuestName");
            int questType= resultSet.getInt("QuestType");
            QuestIdentifier questIdentifier = new QuestIdentifier(questId,questName,questType);

            switch (questType){
                case 1:
                    questMap.get(1).add(questIdentifier);
                    break;
                case 2:
                    questMap.get(2).add(questIdentifier);
                    break;
                case 3:
                    questMap.get(3).add(questIdentifier);
                    break;
                default:
                    break;
            }
        }

        return questMap;
    }


}
