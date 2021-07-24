package Model;

import Data.DI.IServiceLocator;
import Data.IDatabase;
import Data.QuestType;
import ViewModel.QuestViewModel;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameController implements IGameController{

    private final IGameSetUp gameSetUp;
    private ArrayList<IGameState> turns = new ArrayList<>();
    private Map<QuestType,ArrayList<QuestIdentifier>> availableQuests = new HashMap<>();
    private final IServiceLocator serviceLocator;

    private static final Random generator = new Random();

    private final double baseMorale;
    private final double baseSupply;


    public GameController(IServiceLocator serviceLocatorIn, IGameSetUp gameSetUp){
        serviceLocator = serviceLocatorIn;
        this.gameSetUp=gameSetUp;
        baseMorale=gameSetUp.getInitialMorale();
        baseSupply=gameSetUp.getInitialSupply();


        // create a copy as we are going to modify this list
        availableQuests=gameSetUp.getAvailableQuests();

        turns.add(makeInitialState(gameSetUp));
    }

    @Override
    public IGameState nextTurn(double difficulty) {
        var prevStep = getCurrentState();
        var completedQuests = prevStep.getCompletedQuests();

        //Check for end game
        if(prevStep.getCurrentDay()+1 > gameSetUp.getNumberOfDays()){
            return null;
        }

        GameState nextTurn;

        double currentMorale = prevStep.getMoraleValue();
        double currentSupply = prevStep.getSupplyValue();

        // roll for results
        for(int i=0;i<completedQuests.size();i++){
            double result = rollTheDice(completedQuests.get(i).getData(),difficulty);

            IQuest quest = completedQuests.get(i).getQuest();

            if(result>2.0){
                currentMorale=currentMorale+quest.getMoraleResult(QuestOutcome.DEFAULT);
                currentSupply=currentSupply+quest.getSupplyResult(QuestOutcome.DEFAULT);
            } else if(result<0.3){
                currentMorale=currentMorale+quest.getMoraleResult(QuestOutcome.BAD);
                currentSupply=currentSupply+quest.getSupplyResult(QuestOutcome.BAD);
            } else if(result>=0.3 && result<0.8){
                currentMorale=currentMorale+quest.getMoraleResult(QuestOutcome.NORMAL);
                currentSupply=currentSupply+quest.getSupplyResult(QuestOutcome.NORMAL);
            } else{
                currentMorale=currentMorale+quest.getMoraleResult(QuestOutcome.GOOD);
                currentSupply=currentSupply+quest.getSupplyResult(QuestOutcome.GOOD);
            }
        }

        //check for CRISIS events
        ArrayList<QuestInfo<Point2D>> quests;

        if(prevStep.getCurrentDay()+1 == gameSetUp.getNumberOfDays()){
            quests = takeRandomQuests(1,QuestType.CRISIS_END);
        } else if(currentMorale<=0){
            quests = takeRandomQuests(1,QuestType.CRISIS_MORALE);
        } else if(currentSupply<=0){
            quests = takeRandomQuests(1,QuestType.CRISIS_SUPPLY);
        } else if(prevStep.isSiegeBegun()){
            quests = takeRandomQuests(gameSetUp.getNumberOfQuestsPerDay(),QuestType.SIEGE);
        }  else {
            quests = takeRandomQuests(gameSetUp.getNumberOfQuestsPerDay(),QuestType.PEACE);
        }

        //trigger if runout of quests
//        if(quests.size()==0){
//            quests = takeRandomQuests(1,QuestType.CRISIS_END);
//        }

        nextTurn = new GameState(prevStep.getCurrentDay()+1,currentMorale,currentSupply,quests);
        turns.add(nextTurn);

        IDatabase database = serviceLocator.getService("IDatabase");
        database.saveGame(this,"wow");

        return nextTurn;
    }

    private ArrayList<QuestInfo<Point2D>> takeRandomQuests(int count, QuestType type) {

        ArrayList<QuestInfo<Point2D>> taken = new ArrayList<>();
        IDatabase database = serviceLocator.getService("IDatabase");

        for ( int i = 0; i < count; i++ ) {

            if ( availableQuests.get(type).size() == 0 )
                return taken;

            var index = generator.nextInt(availableQuests.get(type).size());
            var quest = database.getQuestById(availableQuests.get(type).get(index).getQuestId());

            double xCoordinate = generator.nextDouble();
            double yCoordinate = generator.nextDouble();
            Point2D coordinates = new Point2D(xCoordinate,yCoordinate);

            taken.add(new QuestInfo<>(coordinates,quest));

            availableQuests.get(type).remove(index);
        }
        return taken;
    }

    private static double rollTheDice(double modifier,double difficulty){
        double result = generator.nextDouble() +modifier+difficulty;
//        System.out.println(result);
        if(modifier>2){
            return 2.0;
        } else if(result<0.0){
            return 0.0;
        } else if(result>1.0){
            return 1.0;
        } else{
            return result;
        }

    }

    @Override
    public IGameState getInitialState() {
        return turns.get(0);
    }

    private IGameState makeInitialState(IGameSetUp gameSetUp) {
        double currentMorale = randomiseScaleValue(baseMorale);
        double currentSupply = randomiseScaleValue(baseSupply);

        GameState gameState = new GameState(0,currentMorale,currentSupply,new ArrayList<>());
        return gameState;
    }

    private static double randomiseScaleValue(double base) {
        double value = Math.round((generator.nextDouble()*(1-0.5) + base)*10)/10.0;
        if(value>1){
            return 1;
        } else if (value<0){
            return 0.5;
        }
        return value;
    }

    @Override
    public ArrayList<IGameState> getPreviousTurns() {
        return turns;
    }

    private IGameState getCurrentState(){
        return turns.get(turns.size()-1);
    }

    @Override
    public IGameSetUp getGameSetUp(){
        return gameSetUp;
    }

    @Override
    public void completeQuest(IQuest quest, double modifier) {

        var completedQuests = getCurrentState().getCompletedQuests();
        for (var item: completedQuests){
            if(item.getQuest().getQuestId()==quest.getQuestId()){
                return;
            }
        }

        boolean found = false;
        for (var item: getCurrentState().getQuests()){
            if(item.getQuest().getQuestId()==quest.getQuestId()){
                found = true;
                break;
            }
        }
        if ( !found )
            return;

        var questCompletionInfoHolder=new QuestInfo<>(modifier,quest);
        completedQuests.add(questCompletionInfoHolder);
    }

    @Override
    public boolean canCompleteAnotherQuest() {

        if(getCurrentState() == getPreviousTurns().get(getPreviousTurns().size()-1)){
            if(getCurrentState().getCompletedQuests().size()<gameSetUp.getQuestSelectCountPerDay()){
                return true;
            }
        }
        return false;
    }

}
