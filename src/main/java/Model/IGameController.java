package Model;


import java.util.ArrayList;

public interface IGameController {

    IGameState nextTurn(double difficulty);
    IGameState getInitialState();

    ArrayList<IGameState> getPreviousTurns();
    IGameSetUp getGameSetUp();

    void completeQuest(IQuest quest, double modifier);

    boolean canCompleteAnotherQuest();
}
