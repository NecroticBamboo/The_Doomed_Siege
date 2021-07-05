package GUI;

import Data.DI.IServiceLocator;
import Data.DI.ServiceLocator;
import Data.Database;
import Data.IDatabase;
import Data.utils.DatabaseConnection;
import Model.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;


public class StartMainMenu extends Application {

    private static final String title=
            "████████╗██╗  ██╗███████╗    ██████╗  ██████╗  ██████╗ ███╗   ███╗███████╗██████╗ \n" +
            "╚══██╔══╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██╔═══██╗████╗ ████║██╔════╝██╔══██╗\n" +
            "   ██║   ███████║█████╗      ██║  ██║██║   ██║██║   ██║██╔████╔██║█████╗  ██║  ██║\n" +
            "   ██║   ██╔══██║██╔══╝      ██║  ██║██║   ██║██║   ██║██║╚██╔╝██║██╔══╝  ██║  ██║\n" +
            "   ██║   ██║  ██║███████╗    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚═╝ ██║███████╗██████╔╝\n" +
            "   ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═════╝ \n" +
            "                                                                                  \n" +
            "        ███████╗██╗███████╗ ██████╗ ███████╗                                      \n" +
            "        ██╔════╝██║██╔════╝██╔════╝ ██╔════╝                                      \n" +
            "        ███████╗██║█████╗  ██║  ███╗█████╗                                        \n" +
            "        ╚════██║██║██╔══╝  ██║   ██║██╔══╝                                        \n" +
            "        ███████║██║███████╗╚██████╔╝███████╗                                      \n" +
            "        ╚══════╝╚═╝╚══════╝ ╚═════╝ ╚══════╝                                      \n";

    @Override
    public void start(Stage primaryStage) {

        ServiceLocator serviceLocator = new ServiceLocator();
        serviceLocator.setService("IDatabaseManager",new DatabaseConnection());
        serviceLocator.setService("IDatabase",new Database(serviceLocator));

//        Database database = new Database(serviceLocator);
//        Quest quest = database.getQuestById(1);
//        System.out.println(quest.getQuestId());
//        System.out.println(quest.getQuestName());
//        System.out.println(quest.getQuestDescription());
//        System.out.println(quest.getQuestType());
//        System.out.println(quest.isUsed());
//        System.out.println(quest.getMoraleBadResult());
//        System.out.println(quest.getSupplyBadResult());
//        System.out.println(quest.getMoraleNormalResult());
//        System.out.println(quest.getSupplyNormalResult());
//        System.out.println(quest.getMoraleGoodResult());
//        System.out.println(quest.getSupplyGoodResult());
//        System.out.println(quest.getMoraleDefaultResult());
//        System.out.println(quest.getSupplyDefaultResult());

        Scene[] scenes = new Scene[5];

        var mainMenuScene = mainMenuInit(primaryStage,scenes,serviceLocator);

//        var playClassicScene = PlayClassicMenu.init(primaryStage,scenes,serviceLocator);

        var playCustomMenuScene = PlayCustomMenu.init(primaryStage,scenes,serviceLocator);

        var infoScene = InfoMenu.init(primaryStage,scenes);

        var optionsScene = OptionsMenu.init(primaryStage,scenes);

        scenes[0]=mainMenuScene;
//        scenes[1]=playClassicScene;
        scenes[2]=playCustomMenuScene;
        scenes[3]=infoScene;
        scenes[4]=optionsScene;

        primaryStage.setScene(mainMenuScene);

        primaryStage.setMinWidth(720);
        primaryStage.setMinHeight(400);
//        primaryStage.setMaxWidth(890);
//        primaryStage.setMaxHeight(870);

        primaryStage.setTitle("The Doomed Siege");
        primaryStage.show();
    }

    private static Scene mainMenuInit(Stage primaryStage, Scene[] scenes, IServiceLocator serviceLocator) {

        Text titleArea = new Text(title);
        titleArea.setFont(Font.font("Courier New",14));

        MenuButton playButton = new MenuButton("Play");
        playButton.setPrefWidth(100);
        playButton.setAlignment(Pos.CENTER);

        MenuItem playClassic= new MenuItem("Play Classic");
        MenuItem playCustom = new MenuItem("Play Custom WIP");
        MenuItem loadGame = new MenuItem("Load Game");

        playButton.getItems().add(playClassic);
        playButton.getItems().add(playCustom);
        playButton.getItems().add(loadGame);

        Button infoButton = new Button("Info/Rules");
        infoButton.setPrefWidth(100);
        Button optionsButton = new Button("Options");
        optionsButton.setPrefWidth(100);
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(100);

        VBox buttonsGroup = new VBox(10);
        buttonsGroup.setAlignment(Pos.CENTER);
        buttonsGroup.getChildren().addAll(playButton,infoButton,optionsButton,exitButton);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titleArea,buttonsGroup);

        Scene scene = new Scene(root,750,400);

        playClassic.setOnAction(e->{

            IGameState initialGameState = setUpClassicGame(serviceLocator);
            var playClassicScene =  PlayClassicMenu.init(primaryStage,scenes,initialGameState); //CREATES SCENE TWO TIMES. NOT NEEDED
            scenes[1]=playClassicScene;

            primaryStage.setResizable(false);
//            primaryStage.setMaxWidth(1780);
//            primaryStage.setMaxHeight(1740);
            primaryStage.setScene(scenes[1]);
        });

        playCustom.setOnAction(e->{
            primaryStage.setScene(scenes[2]);
        });

        loadGame.setOnAction(e->{

        });

        infoButton.setOnAction(e->{
            primaryStage.setScene(scenes[3]);
        });

        optionsButton.setOnAction(e->{
            primaryStage.setScene(scenes[4]);
        });

        exitButton.setOnAction(e->{
            System.exit(1);
        });

        return scene;
    }

    private static IGameState setUpClassicGame(IServiceLocator serviceLocator){
        try {
            return setUpClassicGameUnsafe( serviceLocator );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static IGameState setUpClassicGameUnsafe(IServiceLocator serviceLocator) throws SQLException {
        IDatabase database = serviceLocator.getService("IDatabase");
        IGameSetUp setUp = database.getGameById(1);

        IGameController gameController = new GameController(serviceLocator,setUp);
        IGameState gameState =gameController.getInitialState();
        return gameState;
    }


}
