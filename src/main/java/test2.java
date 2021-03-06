/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author reegan
 */
public class test2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        ScrollPane root = new ScrollPane();
        Scene scene = new Scene(root, 300, 250);
        Text text = new Text("The look and feel of JavaFX applications "
                + "can be customized. Cascading Style Sheets (CSS) separate "
                + "appearance and style from implementation so that developers can "
                + "concentrate on coding. Graphic designers can easily "
                + "customize the appearance and style of the application "
                + "through the CSS. If you have a web design background,"
                + " or if you would like to separate the user interface (UI) "
                + "and the back-end logic, then you can develop the presentation"
                + " aspects of the UI in the FXML scripting language and use Java "
                + "code for the application logic. If you prefer to design UIs "
                + "without writing code, then use JavaFX Scene Builder. As you design the UI, "
                + "Scene Builder creates FXML markup that can be ported to an Integrated Development "
                + "Environment (IDE) so that developers can add the business logic.");
        text.wrappingWidthProperty().bind(scene.widthProperty());
        root.setFitToWidth(true);
        root.setContent(text);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}