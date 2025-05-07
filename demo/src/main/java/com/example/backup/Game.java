package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Game {
    private Player myplayers[];
    private Trail mytrail;
    private Inventory myinventory;

    public Game() {
        myplayers=new Player[5];
        mytrail=new Trail();
        myinventory=new Inventory();

        for(int i=0;i<5;i++) myplayers[i]=new Player();
    }

    public void play(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");

        // Labels for player status and inventory
        Label healthLabel[]=new Label[5];
        for(int i=0;i<5;i++){
            healthLabel[i] = new Label("player "+ Integer.toString(i+1) + " Health: " + myplayers[i].playerStatus());
        }
        Label foodLabel = new Label("Food: " + myinventory.getFood());
        Label cashLabel = new Label("Cash: " + myinventory.getCash());

        // Event button
        Button eventButton = new Button("Next Event");
        eventButton.setOnAction(e -> {
            Events.generateRandomEvent(myplayers, myinventory);
            for(int i=0;i<5;i++) {
                layout.getChildren().remove(healthLabel[i]);
                healthLabel[i].setText("player "+ Integer.toString(i+1) + " Health: " + myplayers[i].playerStatus());
                layout.getChildren().add(healthLabel[i]);
            }
            foodLabel.setText("Food: " + myinventory.getFood());
            cashLabel.setText("Cash: " + myinventory.getCash());
            checkGameOver(stage);
        });

        layout.getChildren().addAll(foodLabel, cashLabel, eventButton);
        Scene gameScene = new Scene(layout, 400, 400);
        stage.setScene(gameScene);
    }

    private void checkGameOver(Stage stage) {
        boolean isalive=false;
        for(int i=0;i<5;i++) if(myplayers[i].playerAlive()) isalive=isalive||true;
        if (!isalive || mytrail.theEnd()) {
            VBox gameOverLayout = new VBox(20);
            gameOverLayout.setStyle("-fx-padding: 20;");
            Label gameOverLabel = new Label("Game Over! You did not survive the journey.");
            Button restartButton = new Button("Restart Game");
            restartButton.setOnAction(e -> new App().start(stage)); // Restart the game
            gameOverLayout.getChildren().addAll(gameOverLabel, restartButton);

            Scene gameOverScene = new Scene(gameOverLayout, 400, 200);
            stage.setScene(gameOverScene);
        }
        mytrail.checkpoint();
    }
}
