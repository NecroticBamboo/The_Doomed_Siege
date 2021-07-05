package GUI;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class OptionsMenu {

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

    public static Scene init(Stage primaryStage, Scene[] scenes){
        Text titleArea= new Text(title);
        titleArea.setFont(Font.font("Courier New",14));
        VBox top = new VBox(5);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding(new Insets(15,0,0,0));
        top.getChildren().add(titleArea);

        Button testButton1 = new Button("Test Button 1");
        Button testButton2 = new Button("Test Button 2");
        Text text=new Text("This is a test text or smt.");

        VBox options = new VBox(10);
        options.setAlignment(Pos.CENTER);
        options.getChildren().addAll(testButton1,testButton2,text);

        ScrollPane optionsBar = new ScrollPane();
        optionsBar.setContent(options);
        options.minWidthProperty().bind(Bindings.createDoubleBinding(()->optionsBar.getViewportBounds().getWidth(),optionsBar.viewportBoundsProperty()));

        Button mainMenuButton = new Button("To Main Menu");

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(top,optionsBar,mainMenuButton);

        Scene scene = new Scene(root,750,400);

        mainMenuButton.setOnAction(e->{
            primaryStage.setScene(scenes[0]);
        });

        return scene;
    }
}
