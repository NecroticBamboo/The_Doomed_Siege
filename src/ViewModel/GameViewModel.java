package ViewModel;

import Model.IGameController;
import Model.IGameState;
import Model.QuestInfo;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GameViewModel {

    private final IGameController gameController;

    private IGameState currentState;
    private ArrayList<QuestViewModel> questViewModel = new ArrayList<>();

    public GameViewModel(IGameController gameControllerIn){
        gameController=gameControllerIn;
        currentState=gameController.getInitialState();
    }

    public int getMoraleValue(){
        return (int) Math.rint(currentState.getMoraleValue()*100.0);
    }

    public int getSupplyValue(){
        return (int) Math.rint(currentState.getSupplyValue()*100.0);
    }

    //needed for the slider
    public int getNumberOfDays(){
        return gameController.getGameSetUp().getNumberOfDays();
    }

    //needed for the slider
    public void setTurn(int day){
        currentState = gameController.getPreviousTurns().get(day);
    }

    public String getMapURL(){
        return gameController.getGameSetUp().getMapUrl();
    }

    public double getDifficulty(){
        return gameController.getGameSetUp().getDifficulty();
    }

    public int getNumberOfQuestsPerDay(){
        return gameController.getGameSetUp().getNumberOfQuestsPerDay();
    }

    public int getQuestSelectCountPerDay(){return gameController.getGameSetUp().getQuestSelectCountPerDay();}


//    private int bruh =0;

    //needed for next turn button
    public void nextTurn(double difficulty){
        currentState = gameController.nextTurn(difficulty);

        questViewModel.clear();
        for(var item: currentState.getQuests()){
            questViewModel.add(new QuestViewModel(item.getQuest()));
        }
    }

    public ArrayList<Point2D> getCoordinates(double maxWidth, double maxHeight){                      //????????????????
        ArrayList<Point2D> res = new ArrayList<>();
        for(int i=0;i<currentState.getQuests().size();i++){

            Point2D oldPoint = currentState.getQuests().get(i).getData();

            double xCor = oldPoint.getX()*(maxWidth - 128) + 64 - 20;
            double yCor = oldPoint.getY()*(maxHeight - 128) + 64 - 20;
            Point2D newPoint = new Point2D(xCor,yCor);

            res.add(newPoint);
        }
        return res;
    }

    public String getEventLogContents(int day){
//        if(questCompletionInfoHolder==null){
//            return currentState.getGameState();
//        }
//
//        String res = currentState.getGameState()+questCompletionInfoHolder.getQuest().getQuestName()+" was completed.\n";
//        return res;
        return gameController.getPreviousTurns().get(day).getGameState();
    }

    public void completeQuest(QuestViewModel questViewModel){
        var quest = questViewModel.getQuest();
        var modifier = questViewModel.getModifier()/100.0;
        gameController.completeQuest(quest,modifier);
    }

    public ArrayList<QuestViewModel> getQuestView(){
        return questViewModel;
    }
}
