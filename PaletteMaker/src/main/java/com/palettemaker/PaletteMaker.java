package com.palettemaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaletteMaker extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                PaletteMaker.class.getResource("hello-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 1000, 700);

        scene.getStylesheets().add(
                PaletteMaker.class.getResource("style.css").toExternalForm()
        );

        stage.setTitle("Palette Maker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}