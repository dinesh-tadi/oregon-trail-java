package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Oregon Trail Game");

        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20;");

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            Game mygame = new Game(); 
            mygame.play(primaryStage);
        });

        root.getChildren().add(startButton);
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
