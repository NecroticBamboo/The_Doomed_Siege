package Model;

import Data.DI.IServiceLocator;
import Data.IDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameController implements IGameController{

    private final IGameSetUp gameSetUp;
    private ArrayList<IGameState> turns = new ArrayList<>();
    private Map<Integer,ArrayList<QuestIdentifier>> availableQuests = new HashMap<>();
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
//        for( var item : gameSetUp.getAvailableQuests() )
//            availableQuests.add(item);

        turns.add(makeInitialState(gameSetUp));
    }

    private ArrayList<IQuest> takeRandomQuests(int count,int type) {

        ArrayList<IQuest> taken = new ArrayList<>();
        IDatabase database = serviceLocator.getService("IDatabase");

        for ( int i = 0; i < count; i++ ) {

            if ( availableQuests.size() == 0 )
                return taken;

            var index = generator.nextInt(availableQuests.get(type).size());
            var quest = database.getQuestById(availableQuests.get(type).get(index).getQuestId());
            taken.add(quest);
            availableQuests.get(type).remove(index);
        }
        return taken;
    }

    @Override
    public IGameState nextTurn(double difficulty,ArrayList<QuestCompletionInfo> completedQuests) {
        var prevStep = turns.get(turns.size()-1);

        double currentMorale = prevStep.getMoraleValue();
        double currentSupply = prevStep.getSupplyValue();
//        double result = rollTheDice(modifier,difficulty);

        var quests = takeRandomQuests(gameSetUp.getNumberOfQuestsPerDay(),1);
        // if quests.size() == 0 end of the game?

        GameState gameState = new GameState(gameSetUp.getNumberOfDays(),currentMorale,currentSupply,quests);
        return gameState;
    }

    private static double rollForResultMorale(double valueToChange, IQuest quest, double result){

//        if(result<0.4){
//            valueToChange=valueToChange+quest.getMoraleBadResult();
//        } else if(result>=0.4 && result<0.8){
//            valueToChange=valueToChange+quest.getMoraleNormalResult();
//        } else {
//            valueToChange=valueToChange+ quest.getMoraleGoodResult();
//            if (valueToChange>1.0){
//                valueToChange=1.0;
//            }
//        }
        return valueToChange;
    }

    private static double rollForResultSupply(double valueToChange, IQuest quest, double result){
//        if(result<0.4){
//            valueToChange=valueToChange+quest.getSupplyBadResult();
//        } else if(result>=0.4 && result<0.8){
//            valueToChange=valueToChange+quest.getSupplyNormalResult();
//        } else {
//            valueToChange=valueToChange+ quest.getSupplyGoodResult();
//            if (valueToChange>1.0){
//                valueToChange=1.0;
//            }
//        }
        return valueToChange;
    }

    private static double rollTheDice(double modifier,double difficulty){
        double result = generator.nextDouble() +modifier+difficulty;
        if(result<0.0){
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

        GameState gameState = new GameState(gameSetUp.getNumberOfDays(),currentMorale,currentSupply,new ArrayList<>());
        return gameState;
    }

    @Override
    public ArrayList<IGameState> getPreviousTurns() {
        return turns;
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
}
