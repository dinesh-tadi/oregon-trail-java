package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Oregon Trail Game - Retro Edition");
        primaryStage.setWidth(900);
        // primaryStage.setHeight(600);

        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20; -fx-background-color: #000000;");

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");

        startButton.setOnAction(e -> {
            Game mygame = new Game();
            mygame.chooseRole(primaryStage);
        });

        root.getChildren().add(startButton);
        Scene scene = new Scene(root, 900, 600, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
