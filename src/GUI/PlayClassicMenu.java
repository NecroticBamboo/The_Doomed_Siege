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
    private GridPane eventLogQuestContent;
    private Slider siegeDaysAndNightsTracker;

    private ArrayList<Button> buttons;

    private Button nextTurnButton;
    private Button submitButton;

    private double maxWidth;
    private double maxHeight;
    private String eventLogText;

    public PlayClassicMenu(Stage primaryStage, Scene[] scenes, GameViewModel gameViewModelIn){
        gameViewModel=gameViewModelIn;
        eventLogText=gameViewModel.getEventLogContents();

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

    private void updateUI(){

        eventLogContents.setText(gameViewModel.getEventLogContents());

        var canCompleteMore = gameViewModel.canCompleteAnotherQuest();
        nextTurnButton.setDisable(canCompleteMore);
        if(submitButton!=null){
            submitButton.setDisable(!canCompleteMore || gameViewModel.wasQuestComplete(gameViewModel.getCurrentQuest()));
        }

        if(gameViewModel.getCurrentQuest()!=null){
            eventLog.setContent(eventLogQuestContent);
        } else {
            eventLog.setContent(eventLogContents);
        }

        for (int i=0;i<buttons.size();i++){
            assignQuest(buttons.get(i),i);
        }

        moveQuestButtons(buttons);
    }

    public Scene getScene(){
        return scene;
    }

    private Slider getSiegeDaysAndNightsTracker() {
        Slider tracker = new Slider(0,gameViewModel.getNumberOfDays(),0);
        tracker.setShowTickLabels(true);
        tracker.setShowTickMarks(true);
        tracker.setMajorTickUnit(1);
        tracker.setMinorTickCount(0);
        tracker.setSnapToTicks(true);

        tracker.setOnMouseReleased(e->{
//            System.out.println("Tracker value: "+tracker.getValue());
            gameViewModel.setTurn((int) tracker.getValue());
            gameViewModel.setCurrentQuest(null);
            updateUI();
        });

        return tracker;
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
        Image mapImage = new Image(gameViewModel.getMapURL());
        ImageView map = new ImageView();
        map.setFitHeight(800);
        map.setFitWidth(1000);
        map.setImage(mapImage);

        maxWidth= map.getFitWidth();
        maxHeight= map.getFitHeight();

        Pane mapPane = new Pane();
        mapPane.getChildren().add(map);

        return mapPane;
    }

    private ArrayList<Button> createQuests(int numberOfQuests){
        buttons = new ArrayList<>();

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

            assignQuest(questButton, i);

            buttons.add(questButton);
        }
        moveQuestButtons(buttons);

        return buttons;
    }

    private void assignQuest(Button questButton, int finalI) {
        questButton.setOnAction(e->{

            QuestViewModel quest;
            if(gameViewModel.getQuestView().size()==0){
                quest=new QuestViewModel(new Quest(100,"Test Holder"," ", QuestType.PEACE,true,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
            } else {
                quest = gameViewModel.getQuestView().get(finalI);
            }
            eventLogQuestContent=getQuestMenu(quest);
            gameViewModel.setCurrentQuest(quest);
            updateUI();
        });
    }

    private void moveQuestButtons(ArrayList<Button> questButtons){

        ArrayList<Point2D> coordinates = gameViewModel.getCoordinates(maxWidth,maxHeight);

        for (int i=0;i<coordinates.size();i++) {
            questButtons.get(i).setLayoutY(coordinates.get(i).getY());
            questButtons.get(i).setLayoutX(coordinates.get(i).getX());
            questButtons.get(i).setVisible(true);
        }

        for(int i=coordinates.size();i<questButtons.size();i++){
            questButtons.get(i).setVisible(false);
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
        nextTurnButton = new Button("Next Turn");
        nextTurnButton.setPrefWidth(240);
        nextTurnButton.setPrefHeight(70);
        buttonBox.getChildren().add(nextTurnButton);

        nextTurnButton.setOnAction(e->{
            gameViewModel.nextTurn(gameViewModel.getDifficulty());
            ArrayList<Button> newQuestSet;

            if (siegeDaysAndNightsTracker.getValue()==0){
                newQuestSet = createQuests(gameViewModel.getNumberOfQuestsPerDay());
                mapPane.getChildren().addAll(newQuestSet);
            }

            buttons= new ArrayList<>();
            var children = mapPane.getChildren();
            for (javafx.scene.Node b : children) {
                if (b instanceof Button) {
                    buttons.add((Button) b);
                }
            }
            moveQuestButtons(buttons);

            siegeDaysAndNightsTracker.setValue(gameViewModel.getLastDay());
            eventLogContents.setText(gameViewModel.getEventLogContents());

            nextTurnButton.setDisable(true);

            updateUI();
        });

        eventLogBox.getChildren().addAll(scalesBox,eventLog,buttonBox);
        return eventLogBox;
    }

    private GridPane getQuestMenu(QuestViewModel quest){
        GridPane questInfoBox = new GridPane();
        questInfoBox.getRowConstraints().addAll(new RowConstraints(470),new RowConstraints(200));

        VBox text = new VBox();
        text.setAlignment(Pos.TOP_LEFT);
        Text questText = new Text("Quest name: "+quest.getName()+"\nQuest description: "+quest.getDescription());
        questText.setWrappingWidth(225);
        text.getChildren().add(questText);


        VBox modifierAndSubmitBox=new VBox(5);
        modifierAndSubmitBox.setAlignment(Pos.BOTTOM_CENTER);

        ComboBox<Integer> modifierComboBox = new ComboBox<>();
        modifierComboBox.getItems().addAll(-10,0,+10,+20,+30);
        modifierComboBox.setValue(0);


        submitButton = new Button("Submit");
        submitButton.setPrefWidth(240);
        submitButton.setPrefHeight(50);

        Button backButton = new Button("Back");
        backButton.setPrefWidth(240);
        backButton.setPrefHeight(20);

        modifierAndSubmitBox.getChildren().addAll(modifierComboBox,submitButton, backButton);
        questInfoBox.add(text,0,0);
        questInfoBox.add(modifierAndSubmitBox,0,1);

        backButton.setOnAction(e->{
//            eventLog.setContent(eventLogContents);
            gameViewModel.setCurrentQuest(null);
            updateUI();
        });

        if(gameViewModel.canCompleteAnotherQuest()){
            submitButton.setOnAction(e->{
                gameViewModel.completeQuest(quest);
                submitButton.setDisable(true);

                updateUI();
            });
        }

        return questInfoBox;
    }

    private HBox createScale(double initialValue, Image image){
        HBox scaleBox = new HBox(5);
        scaleBox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(image);

        ProgressBar scale = new ProgressBar();
        scale.setProgress(initialValue/100.0);

        Text scaleValue = new Text(scale.getProgress()*100+"%");

        scaleBox.getChildren().addAll(imageView,scale,scaleValue);
        return scaleBox;
    }

}