package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InfoMenu {
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


    // Change them to txt files
    private static final String historyText="The End Times has come and we can't stop it. As Archaon the Everchosen destroys all in far North, another threat came from the unexpected front...\n\n\n\n\n\n\n\n\n\n";
    private static final String rulesText="Here is how to play The Doomed Siege...";

    public static Scene init(Stage primaryStage,Scene[] scenes){
        Text titleArea=new Text(title);
        titleArea.setFont(Font.font("Courier New",14));

        Button historyButton = new Button("The Story");
        historyButton.setMaxWidth(Double.MAX_VALUE);
        historyButton.setDisable(true);

        Button rulesButton = new Button("Rules");
        rulesButton.setMaxWidth(Double.MAX_VALUE);

        Button mainMenuButton = new Button("To Main Menu");
        mainMenuButton.setMaxWidth(Double.MAX_VALUE);

        HBox buttonsGroup=new HBox(0);
        buttonsGroup.setAlignment(Pos.CENTER);
        HBox.setHgrow(historyButton, Priority.ALWAYS);
        HBox.setMargin(historyButton,new Insets(0,0,0,5));
        HBox.setHgrow(rulesButton,Priority.ALWAYS);
        HBox.setHgrow(mainMenuButton,Priority.ALWAYS);
        HBox.setMargin(mainMenuButton,new Insets(0,5,0,0));
        buttonsGroup.getChildren().addAll(historyButton,rulesButton,mainMenuButton);

        VBox top = new VBox(5);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding(new Insets(15,0,0,0));
        top.getChildren().addAll(titleArea,buttonsGroup);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Text contents = new Text(historyText);

        scrollPane.setContent(contents);

        VBox root=new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(top,scrollPane);


        //Maybe use GridPane?
//        BorderPane root = new BorderPane();
//        root.setTop(titleArea);
//        root.setCenter(buttonsGroup);
//        root.setBottom(contents);

        Scene scene = new Scene(root,750,400);
        contents.wrappingWidthProperty().bind(scene.widthProperty().subtract(10));

        historyButton.setOnAction(e->{
            contents.setText(historyText);
            historyButton.setDisable(true);
            rulesButton.setDisable(false);
        });

        rulesButton.setOnAction(e->{
            contents.setText(rulesText);
            rulesButton.setDisable(true);
            historyButton.setDisable(false);
        });

        mainMenuButton.setOnAction(e->{
            primaryStage.setScene(scenes[0]);
        });

        return scene;
    }

}
