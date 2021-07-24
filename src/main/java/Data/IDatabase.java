package Data;

import Model.IGameController;
import Model.IGameSetUp;
import Model.IQuest;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IDatabase {

//    void addQuest(IQuest newQuest);
//    void removeQuestById(int questId);

    IQuest getQuestById(int questId);

    IQuest getQuestByName(String questName);
    ArrayList<IQuest> getQuestsByType(QuestType questType);

    IGameSetUp getGameById(int gameId) throws SQLException;

    ArrayList<String> listSavedGames();
    void saveGame(IGameController controller, String gameName);
    IGameController loadSavedGame(String gameName);
    void deleteSavedGame(String gameName);
}
