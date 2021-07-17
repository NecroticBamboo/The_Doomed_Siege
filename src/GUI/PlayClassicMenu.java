package GUI;

import Data.QuestType;
import Model.Quest;
import ViewModel.GameViewModel;
import ViewModel.QuestViewModel;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PlayClassicMenu {

    private final Scene scene;
    private final GameViewModel gameViewModel;

    private ScrollPane eventLog;
    private Text eventLogContents;
    private Slider siegeDaysAndNightsTracker;

    private ImageView map;
    private String eventLogText;

    private int barrier = 0;

    public PlayClassicMenu(Stage primaryStage, Scene[] scenes, GameViewModel gameViewModelIn){
        gameViewModel=gameViewModelIn;
        eventLogText=gameViewModel.getEventLogContents(0);

        GridPane layout = new GridPane();
        layout.setGridLinesVisible(true);
        layout.getColumnConstraints().addAll(new ColumnConstraints(1000),new ColumnConstraints(240));
        layout.getRowConstraints().addAll(new RowConstraints(50),new RowConstraints(800));

        siegeDaysAndNightsTracker = getSiegeDaysAndNightsTracker();

        Pane map = getMap();

        HBox optionsMenu = getOptionsMenu(primaryStage, scenes);

        VBox eventLogBox = getEventLogBox(map);

        layout.add(siegeDaysAndNightsTracker,0,0);
        layout.add(optionsMenu,1,0);
        layout.add(map,0,1);
        layout.add(eventLogBox,1,1);

        VBox root = new VBox(5);
        root.getChildren().add(layout);
        scene = new Scene(root,1240,850);
    }

    public Scene getScene(){
        return scene;
    }

    private Slider getSiegeDaysAndNightsTracker() {
        Slider siegeDaysAndNightsTracker = new Slider(0,gameViewModel.getNumberOfDays(),0);
        siegeDaysAndNightsTracker.setShowTickLabels(true);
        siegeDaysAndNightsTracker.setShowTickMarks(true);
        siegeDaysAndNightsTracker.setMajorTickUnit(1);
        siegeDaysAndNightsTracker.setMinorTickCount(0);
        siegeDaysAndNightsTracker.setSnapToTicks(true);
        return siegeDaysAndNightsTracker;
    }

    private HBox getOptionsMenu(Stage primaryStage, Scene[] scenes) {
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

    private Pane getMap() {
        Image mapImage = new Image(gameViewModel.getMapURL());  //ALLOW IT TO BE DIFFERENT NOT PREASSIGNED
        map =new ImageView();
        map.setFitHeight(800);
        map.setFitWidth(1000);
        map.setImage(mapImage);

//        System.out.println("mapX: "+map.getFitWidth()+" Y: "+map.getFitHeight());

        Pane mapPane = new Pane();
        mapPane.getChildren().add(map);

//        quests.get(0).setOnAction(e->{
//            System.out.println("Button 1 is pressed!");
//        });

        return mapPane;
    }

    private ArrayList<Button> createQuests(int numberOfQuests,double maxWidth,double maxHeight){
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

            int finalI = i;
            questButton.setOnAction(e->{
                if(gameViewModel.getQuestView().size()==0){
                    eventLog.setContent(getQuestMenu(new QuestViewModel(new Quest(100,"Test Holder"," ", QuestType.PEACE,true,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0))));
                } else {
                    QuestViewModel quest = gameViewModel.getQuestView().get(finalI);
                    eventLog.setContent(getQuestMenu(quest));
                }
            });

            quests.add(questButton);
        }
        moveQuestButtons(quests,maxWidth,maxHeight);
        return quests;
    }

    private void moveQuestButtons(ArrayList<Button> questButtons,double maxWidth,double maxHeight){

        ArrayList<Point2D> coordinates = gameViewModel.getCoordinates(maxWidth,maxHeight);          //?????????????????

        for (int i=0;i<questButtons.size();i++) {
            questButtons.get(i).setLayoutY(coordinates.get(i).getY());
            questButtons.get(i).setLayoutX(coordinates.get(i).getX());

//            System.out.println("X: "+questButtons.get(i).getLayoutX()+" Y: "+questButtons.get(i).getLayoutY());
        }
    }

    private VBox getEventLogBox(Pane mapPane) {
        VBox eventLogBox = new VBox(0);

        VBox scalesBox = new VBox(5);

        Image moraleImage = new Image("Assets/morale_icon.png");
        Image supplyImage = new Image("Assets/supply_icon.png");

        HBox moraleBox = createScale(gameViewModel.getMoraleValue(),moraleImage);
        HBox supplyBox = createScale(gameViewModel.getSupplyValue(),supplyImage);

        scalesBox.getChildren().addAll(moraleBox,supplyBox);

        eventLogContents = new Text(eventLogText);
        eventLogContents.setWrappingWidth(240);

        eventLog = new ScrollPane();
        eventLog.setPrefHeight(700);
        eventLog.setContent(eventLogContents);
        eventLog.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        eventLog.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        HBox buttonBox = new HBox();
        Button nextTurnButton = new Button("Next Turn");
        nextTurnButton.setPrefWidth(240);
        nextTurnButton.setPrefHeight(70);
        buttonBox.getChildren().add(nextTurnButton);

        // set maxWidth and maxHeight as map constrains
//        ImageView map = (ImageView) mapPane.getChildren().get(0);
//        double mapWidth = map.getFitWidth();
//        double mapHeight = map.getFitHeight();

//        nextTurnButton.setDisable(true);
        nextTurnButton.setOnAction(e->{
            gameViewModel.nextTurn(gameViewModel.getDifficulty());
            ArrayList<Button> newQuestSet;
            barrier=0;

            ArrayList<Button> buttons = new ArrayList<>();


            if (siegeDaysAndNightsTracker.getValue()==0){
                newQuestSet = createQuests(gameViewModel.getNumberOfQuestsPerDay(),map.getFitWidth(),map.getFitHeight());
                mapPane.getChildren().addAll(newQuestSet);
            }
            else if(siegeDaysAndNightsTracker.getValue()+1==gameViewModel.getNumberOfDays() || gameViewModel.getMoraleValue()<0 || gameViewModel.getSupplyValue()<=0){
                newQuestSet = createQuests(1,map.getFitWidth(),map.getFitHeight());
                mapPane.getChildren().addAll(newQuestSet);
//                buttons= new ArrayList<>();
//                var children = mapPane.getChildren();
//                int count=0;
//
//                for(int i=0; i<children.size(); i++){
//                    var b = children.get(i);
//                    if (b instanceof Button && count<=gameViewModel.getNumberOfQuestsPerDay()-1){
//                        b.setVisible(false);
////                        buttons.add((Button) b);
//                        mapPane.getChildren().remove(b);
//                        count++;
//                    } else if (b instanceof Button && count==5){
//                        buttons.add((Button) b);
//                    }
//
//                }
            }
            else {
                buttons= new ArrayList<>();
                var children = mapPane.getChildren();
                for (javafx.scene.Node b : children) {
                    if (b instanceof Button) {
//                        ((Button)b).setVisible(false);
                        buttons.add((Button) b);
                    }
                }

            }
            moveQuestButtons(buttons,map.getFitWidth(),map.getFitHeight());
            siegeDaysAndNightsTracker.setValue(siegeDaysAndNightsTracker.getValue()+1);
            eventLogContents.setText(gameViewModel.getEventLogContents((int) siegeDaysAndNightsTracker.getValue()));
        });

//        nextTurnButton.setOnAction(e->{
//            eventLog.setContent(getQuestMenu());
//        });

        eventLogBox.getChildren().addAll(scalesBox,eventLog,buttonBox);
        return eventLogBox;
    }

    private GridPane getQuestMenu(QuestViewModel quest){
        GridPane questInfoBox = new GridPane();
        questInfoBox.getRowConstraints().addAll(new RowConstraints(470),new RowConstraints(200));

        VBox text = new VBox();
        text.setAlignment(Pos.TOP_LEFT);
        Text questText = new Text("Quest name: "+quest.getName()+"\nQuest description: "+quest.getDescription());
//        Text questText = new Text("Bruh");
        questText.setWrappingWidth(225);
        text.getChildren().add(questText);


        VBox modifierAndSubmitBox=new VBox(5);
        modifierAndSubmitBox.setAlignment(Pos.BOTTOM_CENTER);

        ComboBox<Integer> modifierComboBox = new ComboBox<>();
        modifierComboBox.getItems().addAll(-10,0,+10,+20,+30);
        modifierComboBox.setValue(0);


        Button submitButton = new Button("Submit");
        submitButton.setPrefWidth(240);
        submitButton.setPrefHeight(50);

        Button backButton = new Button("Back");
        backButton.setPrefWidth(240);
        backButton.setPrefHeight(20);

        modifierAndSubmitBox.getChildren().addAll(modifierComboBox,submitButton,backButton);
        questInfoBox.add(text,0,0);
        questInfoBox.add(modifierAndSubmitBox,0,1);

        backButton.setOnAction(e->{
            eventLog.setContent(eventLogContents);
        });

        if(barrier< gameViewModel.getQuestSelectCountPerDay()){
            submitButton.setOnAction(e->{
//            System.out.println(modifierComboBox.getValue());
                gameViewModel.completeQuest(quest);
                barrier++;
                submitButton.setDisable(true);
                eventLogContents.setText(gameViewModel.getEventLogContents((int) siegeDaysAndNightsTracker.getValue()));
            });
        } else {
            submitButton.setDisable(true);
        }

        return questInfoBox;
    }

    private HBox createScale(double initialValue, Image image){
        HBox scaleBox = new HBox(5);
        scaleBox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(image);

        ProgressBar scale = new ProgressBar();
        scale.setProgress(initialValue/100.0);                      //?????

        Text scaleValue = new Text(scale.getProgress()*100+"%");


//        System.out.println(scale.getProgress());                     // TO DELETE

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