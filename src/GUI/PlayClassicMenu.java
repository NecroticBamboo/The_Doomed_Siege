package GUI;

import Model.IGameSetUp;
import Model.IGameState;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class PlayClassicMenu {

    private static Random random = new Random();

    public static Scene init(Stage primaryStage, Scene[] scenes, IGameState gameState){

        GridPane layout = new GridPane();
        layout.setGridLinesVisible(true);
        layout.getColumnConstraints().addAll(new ColumnConstraints(1000),new ColumnConstraints(240));
        layout.getRowConstraints().addAll(new RowConstraints(50),new RowConstraints(800));

        Slider siegeDaysAndNightsTracker = getSiegeDaysAndNightsTracker(gameState);

        Pane map = getMap();

        HBox optionsMenu = getOptionsMenu(primaryStage, scenes);

        VBox eventLogBox = getEventLogBox(map,siegeDaysAndNightsTracker,gameState);

        layout.add(siegeDaysAndNightsTracker,0,0);
        layout.add(optionsMenu,1,0);
        layout.add(map,0,1);
        layout.add(eventLogBox,1,1);

        VBox root = new VBox(5);
        root.getChildren().add(layout);
        Scene scene = new Scene(root,1240,850);
        return scene;
    }

    private static Slider getSiegeDaysAndNightsTracker(IGameState gameState) {
        Slider siegeDaysAndNightsTracker = new Slider(0,7,0);
        siegeDaysAndNightsTracker.setShowTickLabels(true);
        siegeDaysAndNightsTracker.setShowTickMarks(true);
        siegeDaysAndNightsTracker.setMajorTickUnit(1);
        siegeDaysAndNightsTracker.setMinorTickCount(0);
        siegeDaysAndNightsTracker.setSnapToTicks(true);
        return siegeDaysAndNightsTracker;
    }

    private static HBox getOptionsMenu(Stage primaryStage, Scene[] scenes) {
        HBox topRightMenu = new HBox(5);
        topRightMenu.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Save game");
        saveButton.setPrefHeight(40);

        Image settingImage = new Image("Assets/cogs_icon.png");
        ImageView settingsView = new ImageView(settingImage);
        Button settingButton = new Button();
        settingButton.setGraphic(settingsView);

        Image exitImage = new Image("Assets/exit_icon.png");
        ImageView exitView = new ImageView(exitImage);
        Button exitButton = new Button();
        exitButton.setGraphic(exitView);

        topRightMenu.getChildren().addAll(saveButton,settingButton,exitButton);

        exitButton.setOnAction(e -> {
//            primaryStage.setMaxWidth(890);
//            primaryStage.setMaxHeight(870);
            primaryStage.setResizable(true);
            primaryStage.setScene(scenes[0]);
        });
        return topRightMenu;
    }

    private static Pane getMap() {
        Image mapImage = new Image("Assets/Nuln_city.png");  //ALLOW IT TO BE DIFFERENT NOT PREASSIGNED
        ImageView map =new ImageView();
        map.setFitHeight(800);
        map.setFitWidth(1000);
        map.setImage(mapImage);

        Pane mapPane = new Pane();
        mapPane.getChildren().add(map);

//        quests.get(0).setOnAction(e->{
//            System.out.println("Button 1 is pressed!");
//        });

        return mapPane;
    }

    private static ArrayList<Button> createQuests(int numberOfQuests,double maxWidth,double maxHeight){
        ArrayList<Button> quests = new ArrayList<>();

        Image markerImage = new Image("Assets/marker_icon.png");

        for(int i=0;i<numberOfQuests;i++){
            ImageView markerView = new ImageView(markerImage);
            markerView.setFitWidth(40);
            markerView.setFitHeight(40);

            Button questButton = new Button();
            questButton.setGraphic(markerView);
            questButton.setStyle("-fx-border-color: transparent;\n" +
                                 "    -fx-border-width: 0;\n" +
                                 "    -fx-background-radius: 0;\n" +
                                 "    -fx-background-color: transparent;");

            quests.add(questButton);
        }
        moveQuestButtons(quests,maxWidth,maxHeight);
        return quests;
    }

    private static void moveQuestButtons(ArrayList<Button> quests,double maxWidth,double maxHeight){
        for (Button quest : quests) {
            quest.setLayoutY(random.nextDouble() * (maxWidth - 128) + 64 - 20);
            quest.setLayoutX(random.nextDouble() * (maxHeight - 128) + 64 - 20);
        }
    }

    private static VBox getEventLogBox(Pane mapPane,Slider slider,IGameState gameState) {
        VBox eventLogBox = new VBox(0);

        VBox scalesBox = new VBox(5);

        Image moraleImage = new Image("Assets/morale_icon.png");
        Image supplyImage = new Image("Assets/supply_icon.png");

        HBox moraleBox = createScale(gameState.getMoraleValue(),moraleImage);
        HBox supplyBox = createScale(gameState.getSupplyValue(),supplyImage);

        scalesBox.getChildren().addAll(moraleBox,supplyBox);

        Text eventLogContents = new Text("This is a placeholder for contents of the Event Log.");
        eventLogContents.setWrappingWidth(240);

        ScrollPane eventLog = new ScrollPane();
        eventLog.setPrefHeight(700);
        eventLog.setContent(eventLogContents);
        eventLog.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        eventLog.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        HBox buttonBox = new HBox();
        Button nextTurnButton = new Button("Next Turn");
        nextTurnButton.setPrefWidth(240);
        nextTurnButton.setPrefHeight(70);
        buttonBox.getChildren().add(nextTurnButton);

        // set maxWidth and maxHeight as map constrains
//        ImageView map = (ImageView) mapPane.getChildren().get(0);
//        double mapWidth = map.getFitWidth();
//        double mapHeight = map.getFitHeight();

        nextTurnButton.setOnAction(e->{

            if (slider.getValue()==0){
                ArrayList<Button> newQuestSet = createQuests(5,800,1000);
                mapPane.getChildren().addAll(newQuestSet);
            }else{
                ArrayList<Button> buttons= new ArrayList<>();
                var children = mapPane.getChildren();
                for(int i=0; i<children.size(); i++){
                    var b = children.get(i);
                    if (b instanceof Button){
                        buttons.add((Button) b);
                    }
                }
                moveQuestButtons(buttons,800,1000);
            }

            slider.setValue(slider.getValue()+1);

        });

//        nextTurnButton.setOnMouseMoved(e->{
//            ArrayList<Button> newQuestSet = createQuests(5,800,1000);
//            mapPane.getChildren().addAll(newQuestSet);
//        });

        eventLogBox.getChildren().addAll(scalesBox,eventLog,buttonBox);
        return eventLogBox;
    }

    private static HBox createScale(double initialValue, Image image){
        HBox scaleBox = new HBox(5);
        scaleBox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(image);

        ProgressBar scale = new ProgressBar();
        scale.setProgress(initialValue);

        Text scaleValue = new Text(scale.getProgress()*100+"%");


        System.out.println(scale.getProgress());                     // TO DELETE

        scaleBox.getChildren().addAll(imageView,scale,scaleValue);
        return scaleBox;
    }

//    private static double createStartValue(double initValue) {
//        Random generator = new Random();
//        double value = Math.round((generator.nextDouble()*(1-0.5) + initValue)*10)/10.0;
//        if(value>1){
//            return 1;
//        } else if (value<0){
//            return 0.5;
//        }
//        return value;
//    }
}