package GUI;


import Data.DI.IServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayCustomMenu {
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

    private static final ObservableList<String> difficultyOptions = FXCollections.observableArrayList("Very Easy","Easy","Normal","Hard","Very Hard");

    public static Scene init(Stage primaryStage, Scene[] scenes, IServiceLocator serviceLocator){
        Text titleArea=new Text(title);
        titleArea.setFont(Font.font("Courier New",14));

        HBox difficulty = new HBox(5);
        Label difficultyLabel = new Label("Difficulty: ");
        ComboBox difficultyBox = new ComboBox(difficultyOptions);
        difficultyBox.setValue("Normal");
        difficulty.setAlignment(Pos.CENTER);
        difficulty.getChildren().addAll(difficultyLabel,difficultyBox);

        HBox buttonsGroup = new HBox(5);
        Button mainMenuButton = new Button("Main menu");
        Button saveAndProceedButton = new Button("Save and Play");
        buttonsGroup.setAlignment(Pos.CENTER);
        buttonsGroup.getChildren().addAll(mainMenuButton,saveAndProceedButton);


        VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titleArea,difficulty,buttonsGroup);

        Scene scene = new Scene(root,750,400);

        mainMenuButton.setOnAction(e->{
            primaryStage.setScene(scenes[0]);
        });

        return scene;
    }
}
