package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StorePanel {
    private Inventory inventory;
    private int[] prices;
    private String[] items = {
        "50 lbs Food", "20 Bullets", "2 Set of Clothing", "1 Oxen", "1 Set of Axel", "1 Set of Wheels", "1 Tongue", "1 Bottle of Medicine"
    };

    public StorePanel(Inventory inventory) {
        this.inventory = inventory;
        this.prices = new int[]{20, 4, 15, 50, 10, 10, 15, 20};
    }

    public void show(Stage stage, Runnable onExit) {
        VBox marketLayout = new VBox(10);
        marketLayout.setAlignment(Pos.TOP_LEFT);
        marketLayout.setStyle("-fx-padding: 20; -fx-background-color: #000000;");

        Label cashLabel = new Label("Cash Remaining: $" + inventory.getCash());
        cashLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 14px;");

        StringBuilder sb = new StringBuilder("Store Supplies:\n");
        for (int i = 0; i < items.length; i++) {
            sb.append((i+1) + ". " + items[i] + ": $" + prices[i] + "\n");
        }
        Label marketLabel = new Label(sb.toString());
        marketLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 14px;");

        TextField itemInput = new TextField();
        itemInput.setPromptText("Enter item number");
        TextField quantityInput = new TextField();
        quantityInput.setPromptText("Enter quantity");

        Button purchaseButton = new Button("Purchase");
        purchaseButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 14px;");
        purchaseButton.setOnAction(e -> {
            try {
                int itemIndex = Integer.parseInt(itemInput.getText()) - 1;
                int quantity = Integer.parseInt(quantityInput.getText());
                if (itemIndex < 0 || itemIndex >= items.length) return;
                int cost = prices[itemIndex] * quantity;
                if (inventory.getCash() >= cost) {
                    inventory.removeCash(cost);
                    inventory.updateinventory(itemIndex+1, quantity);
                    cashLabel.setText("Cash Remaining: $" + inventory.getCash());
                }
            } catch (NumberFormatException ex) {}
            itemInput.clear();
            quantityInput.clear();
        });

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 14px;");
        exitButton.setOnAction(e -> {
            stage.setScene(stage.getScene()); // Go back to the previous rendered screen
            onExit.run();
        });

        marketLayout.getChildren().addAll(cashLabel, marketLabel, itemInput, quantityInput, purchaseButton, exitButton);
        Scene marketScene = new Scene(marketLayout, 400, 500, Color.BLACK);
        stage.setScene(marketScene);
    }
} 