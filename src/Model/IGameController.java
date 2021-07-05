package Model;

import java.util.ArrayList;

public interface IGameController {

    IGameState nextTurn(double difficulty,ArrayList<QuestCompletionInfo> completedQuests);
    IGameState getInitialState();

    ArrayList<IGameState> getPreviousTurns();

}
