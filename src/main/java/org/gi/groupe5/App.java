package org.gi.groupe5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {


    private final String fenetre = Windows.LOGINVIEW;

    public static void main(String[] args) {
        launch();
    }

    private static Parent loadFXML(String viewFXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewFXML + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(loadFXML(fenetre));
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }


}