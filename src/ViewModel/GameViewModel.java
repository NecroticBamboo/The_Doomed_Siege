package ViewModel;

import Model.IGameController;
import Model.IGameState;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GameViewModel {

    private final IGameController gameController;

    private IGameState currentState;
    private ArrayList<QuestViewModel> questViewModel = new ArrayList<>();
    private QuestViewModel currentQuest;

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
        if(day>=gameController.getPreviousTurns().size()){
            day=gameController.getPreviousTurns().size()-1;
        }
        currentState = gameController.getPreviousTurns().get(day);
        updateQuestViewModel();
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

    public double getCurrentDay(){
        return currentState.getCurrentDay();
    }

    public double getLastDay(){
        return gameController.getPreviousTurns().get(gameController.getPreviousTurns().size()-1).getCurrentDay();
    }

    //needed for next turn button
    public void nextTurn(double difficulty){
        currentState = gameController.nextTurn(difficulty);

        updateQuestViewModel();
    }

    private void updateQuestViewModel() {
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

    public String getEventLogContents(){
        return gameController.getPreviousTurns().get((int) currentState.getCurrentDay()).getGameState();
    }

    public void completeQuest(QuestViewModel questViewModel){
        var quest = questViewModel.getQuest();
        var modifier = questViewModel.getModifier()/100.0;
        gameController.completeQuest(quest,modifier);
    }

    public boolean checkIfCompleted(QuestViewModel questViewModel){
        for(int i=0;i<currentState.getCompletedQuests().size();i++){
            if(questViewModel.getQuest() == currentState.getCompletedQuests().get(i).getQuest()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<QuestViewModel> getQuestView(){
        return questViewModel;
    }

    public QuestViewModel getCurrentQuest(){
        return currentQuest;
    }

    public void setCurrentQuest(QuestViewModel quest){
        currentQuest=quest;
    }

    public boolean canCompleteAnotherQuest(){
        return gameController.canCompleteAnotherQuest();
    }

    public boolean wasQuestComplete(QuestViewModel quest){

        if(quest==null){
            return true;
        }

        for (var q: currentState.getCompletedQuests()){
            if(q.getQuest()==quest.getQuest()){
                return true;
            }
        }
        return false;
    }
}
