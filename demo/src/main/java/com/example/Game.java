package com.example;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game {
    private Player myplayers[];
    private Trail mytrail;
    private Inventory myinventory;
    private FoodRation foodRation;
    private int currentDay;
    private boolean isDaytime;
    private ToggleGroup rationGroup;
    private VBox mainLayout;
    private Scene gameScene;
    private Pane mapPanel;
    private Circle[] checkpointMarkers;
    private Line[] trailLines;
    private StackPane mapContainer;
    private HBox checkpointNamesBox;

    public Game() {
        myplayers = new Player[5];
        mytrail = new Trail();
        myinventory = new Inventory();
        for (int i = 0; i < 5; i++) myplayers[i] = new Player();
        foodRation = new FoodRation(5); // 5 players
        currentDay = 1;
        isDaytime = true;
    }

    // Initial screen to select player role
    public void chooseRole(Stage stage) {
        VBox roleSelectionLayout = new VBox(20);
        roleSelectionLayout.setStyle("-fx-padding: 20; -fx-background-color: #000000;");

        Label roleLabel = new Label("Choose Your Role:");
        roleLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");

        Button farmerButton = new Button("Farmer (Starting Cash: $400)");
        farmerButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 14px;");
        farmerButton.setOnAction(e -> {
            myinventory.setCash(400);
            showOptions(stage);  // Proceed to the main game
        });

        Button carpenterButton = new Button("Carpenter (Starting Cash: $600)");
        carpenterButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 14px;");
        carpenterButton.setOnAction(e -> {
            myinventory.setCash(600);
            showOptions(stage);  // Proceed to the main game
        });

        Button bankerButton = new Button("Banker (Starting Cash: $800)");
        bankerButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 14px;");
        bankerButton.setOnAction(e -> {
            myinventory.setCash(800);
            showOptions(stage);  // Proceed to the main game
        });

        roleSelectionLayout.getChildren().addAll(roleLabel, farmerButton, carpenterButton, bankerButton);
        Scene roleSelectionScene = new Scene(roleSelectionLayout, stage.getWidth(), stage.getHeight(), Color.BLACK);
        stage.setScene(roleSelectionScene);
    }

    // Show options to go to market or continue on the trail
    public void showOptions(Stage stage) {
        VBox fortLayout = new VBox(20);
        fortLayout.setStyle("-fx-padding: 40; -fx-background-color: #000000;");
        // fortLayout.setAlignment(Pos.CENTER);

        Label fortLabel = new Label("You have starting at  independence rock.\n" +
            "You can go to the store or continue on the trail.");
        fortLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 20px;");

        // Label optionsLabel = new Label("Choose an Option:");
        // optionsLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");

        Button storeButton = new Button("Go to Store");
        storeButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
        storeButton.setOnAction(e -> {
            StorePanel store = new StorePanel(myinventory);
            store.show(stage, () -> play(stage));
        });

        Button continueButton = new Button("Continue on Trail");
        continueButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
        continueButton.setOnAction(e -> play(stage));

        fortLayout.getChildren().addAll(fortLabel, storeButton, continueButton);
        Scene fortScene = new Scene(fortLayout, stage.getWidth(), stage.getHeight(), Color.BLACK);
        stage.setScene(fortScene);
    }
    // Main game play method
    public void play(Stage stage) {
        mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-padding: 30; -fx-background-color: #2c3e50;");
        mainLayout.setPrefWidth(900);
        mainLayout.setPrefHeight(600);
        mainLayout.setAlignment(Pos.CENTER);

        // Create and add checkpoint names above the map
        createCheckpointNamesBox();
        mainLayout.getChildren().add(checkpointNamesBox);

        // Create and add map panel at the top, centered
        createMapPanel();
        mapContainer = new StackPane(mapPanel);
        mapContainer.setPrefWidth(900);
        mapContainer.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().add(mapContainer);
        

        // Bind widths to mainLayout width
        mapPanel.prefWidthProperty().bind(mainLayout.widthProperty().subtract(60)); // leave some margin
        checkpointNamesBox.prefWidthProperty().bind(mainLayout.widthProperty().subtract(60));

        // Status Panel
        HBox statusPanel = new HBox(40);
        statusPanel.setAlignment(Pos.CENTER);
        statusPanel.setPrefHeight(50);
        
        // Day/Night indicator with glow effect
        Label timeLabel = new Label(isDaytime ? "☀️ Day" : "🌙 Night");
        timeLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 24px;");
        Glow glow = new Glow();
        glow.setLevel(0.8);
        timeLabel.setEffect(glow);
        
        // Weather display with icon
        Label weatherLabel = new Label(getWeatherIcon(mytrail.getCurrentWeather()) + " " + mytrail.getCurrentWeather());
        weatherLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 24px;");
        weatherLabel.setEffect(glow);
        
        // Day counter
        Label dayLabel = new Label("📅 Day: " + currentDay);
        dayLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 24px;");
        dayLabel.setEffect(glow);
        
        statusPanel.getChildren().addAll(timeLabel, weatherLabel, dayLabel);

        // Resource Panel
        HBox resourcePanel = new HBox(40);
        resourcePanel.setAlignment(Pos.CENTER);
        resourcePanel.setPrefHeight(50);
        
        Label foodLabel = new Label("🍖 Food: " + myinventory.getFood());
        foodLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 20px;");
        
        Label cashLabel = new Label("💰 Cash: " + myinventory.getCash());
        cashLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 20px;");
        
        resourcePanel.getChildren().addAll(foodLabel, cashLabel);

        // Player Status Panel
        VBox playerPanel = new VBox(15);
        playerPanel.setAlignment(Pos.CENTER);
        
        Label[] healthLabel = new Label[5];
        for (int i = 0; i < 5; i++) {
            healthLabel[i] = new Label("👤 Player " + (i + 1) + " Health: " + myplayers[i].playerStatus());
            healthLabel[i].setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 18px;");
            playerPanel.getChildren().add(healthLabel[i]);
        }

        // Ration Control Panel at bottom
        HBox rationPanel = new HBox(20);
        rationPanel.setAlignment(Pos.CENTER);
        rationPanel.setPrefHeight(100);
        rationPanel.setStyle("-fx-background-color: #111111; -fx-padding: 20; -fx-background-radius: 10;");
        
        Label rationTitle = new Label("Food Rations");
        rationTitle.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        rationGroup = new ToggleGroup();
        
        RadioButton starvingButton = createRationButton("🍽️ Starving", FoodRation.RationLevel.STARVING);
        RadioButton scarceButton = createRationButton("🍽️ Scarce", FoodRation.RationLevel.SCARCE);
        RadioButton decentButton = createRationButton("🍽️ Decent", FoodRation.RationLevel.DECENT);
        RadioButton healthyButton = createRationButton("🍽️ Healthy", FoodRation.RationLevel.HEALTHY);
        
        // Set initial selection
        decentButton.setSelected(true);
        
        rationPanel.getChildren().addAll(rationTitle, starvingButton, scarceButton, decentButton, healthyButton);

        // Next Day Button in a centered container
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPrefHeight(100);
        
        Button eventButton = new Button("Next Day");
        eventButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 24px; -fx-min-width: 300; -fx-min-height: 50;");
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setRadius(10);
        shadow.setSpread(0.2);
        eventButton.setEffect(shadow);
        
        buttonContainer.getChildren().add(eventButton);

        // Add all panels to main layout
        mainLayout.getChildren().addAll(statusPanel, resourcePanel, playerPanel, buttonContainer, rationPanel);
        gameScene = new Scene(mainLayout, 900, 600, Color.BLACK);
        updateBackgroundColor();
        stage.setScene(gameScene);

        // Ensure layout update only after window is shown
        gameScene.windowProperty().addListener((obs, oldWin, newWin) -> {
            if (newWin != null) {
                newWin.showingProperty().addListener((obs2, wasShowing, isNowShowing) -> {
                    if (isNowShowing) {
                        mainLayout.applyCss();
                        mainLayout.layout();
                        updateMapLayout();
                        updateCheckpointNamesLayout();
                    }
                });
            }
        });

        // Robust layout update: listen for size changes
        mapPanel.widthProperty().addListener((obs, oldVal, newVal) -> updateMapLayout());
        mapPanel.heightProperty().addListener((obs, oldVal, newVal) -> updateMapLayout());
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateMapLayout();
            updateCheckpointNamesLayout();
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateMapLayout();
            updateCheckpointNamesLayout();
        });

        eventButton.setOnAction(e -> {
            // Toggle day/night with fade transition
            FadeTransition fade = new FadeTransition(Duration.seconds(0.5), mainLayout);
            fade.setFromValue(1.0);
            fade.setToValue(0.3);
            fade.setOnFinished(event -> {
                isDaytime = !isDaytime;
                timeLabel.setText(isDaytime ? "☀️ Day" : "🌙 Night");
                updateBackgroundColor();
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), mainLayout);
                fadeIn.setFromValue(0.3);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fade.play();

            // Update weather with icon
            mytrail.updateWeather();
            weatherLabel.setText(getWeatherIcon(mytrail.getCurrentWeather()) + " " + mytrail.getCurrentWeather());

            // Apply weather effects
            applyWeatherEffects();

            // Consume food for the day
            double foodNeeded = foodRation.getDailyFoodConsumption();
            if (myinventory.getFood() >= foodNeeded) {
                myinventory.consumeFood((int)foodNeeded);
                // Apply health effects
                int healthEffect = foodRation.getDailyHealthEffect();
                for (int i = 0; i < myplayers.length; i++) {
                    if (myplayers[i].playerAlive()) {
                        myplayers[i].playerModifyHealth(healthEffect);
                    }
                }
            } else {
                // Not enough food - everyone takes health penalty
                for (int i = 0; i < myplayers.length; i++) {
                    if (myplayers[i].playerAlive()) {
                        myplayers[i].playerModifyHealth(-15); // Starvation penalty
                    }
                }
            }

            // If at a fort, show fort arrival options
            if (isAtFort()) {
                showFortOptions(stage, mytrail.getCurrentLandmark());
                return;
            }

            // Generate random event using EventManager
            EventManager.triggerRandomEvent(myplayers, myinventory, stage, () -> {
                // Update day counter
                currentDay++;
                dayLabel.setText("📅 Day: " + currentDay);

                // Update all labels
                foodLabel.setText("🍖 Food: " + myinventory.getFood());
                cashLabel.setText("💰 Cash: " + myinventory.getCash());
                for (int i = 0; i < 5; i++) {
                    healthLabel[i].setText("👤 Player " + (i + 1) + " Health: " + (myplayers[i].playerStatus() <= 0 ? "DEAD" : myplayers[i].playerStatus()));
                }

                // Update map
                updateMapLayout();

                checkGameOver(stage);
            });
        });

        // After returning from store/fort, update health labels and check game over
        for (int i = 0; i < 5; i++) {
            healthLabel[i].setText("👤 Player " + (i + 1) + " Health: " + (myplayers[i].playerStatus() <= 0 ? "DEAD" : myplayers[i].playerStatus()));
        }
        checkGameOver(stage);
    }

    private void updateBackgroundColor() {
        if (isDaytime) {
            mainLayout.setStyle("-fx-padding: 30; -fx-background-color: #2c3e50;"); // Blue-gray for day
        } else {
            mainLayout.setStyle("-fx-padding: 30; -fx-background-color: #1a1a2e;"); // Dark blue for night
        }
    }

    private String getWeatherIcon(String weather) {
        if (weather == null) {
            return "🌤️"; // Default weather icon
        }
        switch (weather.toLowerCase()) {
            case "clear": return "☀️";
            case "rainy": return "🌧️";
            case "stormy": return "⛈️";
            case "foggy": return "🌫️";
            case "hot": return "🔥";
            case "cold": return "❄️";
            default: return "🌤️";
        }
    }

    private RadioButton createRationButton(String text, FoodRation.RationLevel level) {
        RadioButton button = new RadioButton(text);
        button.setToggleGroup(rationGroup);
        button.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 18px;");
        button.setOnAction(e -> {
            foodRation.setRationLevel(level);
        });
        return button;
    }

    private void checkGameOver(Stage stage) {
        boolean isAlive = false;
        for (int i = 0; i < 5; i++) if (myplayers[i].playerAlive()) isAlive = true;
        if (!isAlive) {
            VBox gameOverLayout = new VBox(20);
            gameOverLayout.setStyle("-fx-padding: 20; -fx-background-color: #000000;");

            Label gameOverLabel = new Label("Game Over! You did not survive the journey.");
            gameOverLabel.setStyle("-fx-text-fill: #FF0000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
            Button restartButton = new Button("Restart Game");
            restartButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
            restartButton.setOnAction(e -> new App().start(stage)); // Restart the game

            gameOverLayout.getChildren().addAll(gameOverLabel, restartButton);
            Scene gameOverScene = new Scene(gameOverLayout, 400, 200, Color.BLACK);
            stage.setScene(gameOverScene);
        }else if (mytrail.theEnd()) {
            VBox gameOverLayout = new VBox(20);
            gameOverLayout.setStyle("-fx-padding: 20; -fx-background-color: #000000;");

            Label gameOverLabel = new Label("Game Over! You won. You made it to the end of the trail.");
            gameOverLabel.setStyle("-fx-text-fill: #FF0000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
            Button restartButton = new Button("Restart Game");
            restartButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
            restartButton.setOnAction(e -> new App().start(stage)); // Restart the game

            gameOverLayout.getChildren().addAll(gameOverLabel, restartButton);
            Scene gameOverScene = new Scene(gameOverLayout, stage.getWidth(), stage.getHeight(), Color.BLACK);
            stage.setScene(gameOverScene);
        }
        mytrail.checkpoint();
    }

    private void createMapPanel() {
        mapPanel = new Pane();
        mapPanel.setPrefHeight(60);
        mapPanel.setStyle("-fx-background-color: #111111; -fx-padding: 10; -fx-background-radius: 10;");
        mapPanel.setMaxWidth(800);
        mapPanel.setMinWidth(800);
        mapPanel.setPrefWidth(800);

        int numCheckpoints = mytrail.getLandmarks().length;
        checkpointMarkers = new Circle[numCheckpoints];
        trailLines = new Line[numCheckpoints - 1];

        for (int i = 0; i < numCheckpoints; i++) {
            Circle marker = new Circle(2);
            marker.setFill(i <= mytrail.getCurrentCheckpoint() ? Color.GREEN : Color.GRAY);
            checkpointMarkers[i] = marker;
            mapPanel.getChildren().add(marker);

            if (i < numCheckpoints - 1) {
                Line line = new Line();
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(1);
                trailLines[i] = line;
                mapPanel.getChildren().add(line);
            }
        }
        // Listen for width changes to keep layout correct
        mapPanel.widthProperty().addListener((obs, oldVal, newVal) -> updateMapLayout());
        updateMapLayout();
    }

    private void updateMapLayout() {
        double width = mapPanel.getWidth() > 0 ? mapPanel.getWidth() : 800;
        double height = mapPanel.getHeight() > 0 ? mapPanel.getHeight() : 60;
        double padding = 20;
        double availableWidth = width - 2 * padding;
        double y = height / 2;
        int n = checkpointMarkers.length;
        double spacing = (n > 1) ? availableWidth / (n - 1) : 0;
        for (int i = 0; i < n; i++) {
            double x = padding + i * spacing;
            Circle marker = checkpointMarkers[i];
            marker.setCenterX(x);
            marker.setCenterY(y);
            if (i < trailLines.length) {
                Line line = trailLines[i];
                line.setStartX(x + 2);
                line.setStartY(y);
                line.setEndX(padding + (i + 1) * spacing - 2);
                line.setEndY(y);
            }
        }
        for (int i = 0; i < n; i++) {
            checkpointMarkers[i].setFill(i <= mytrail.getCurrentCheckpoint() ? Color.GREEN : Color.GRAY);
        }
    }

    private void applyWeatherEffects() {
        String weather = mytrail.getCurrentWeather();
        int healthEffect = 0;

        switch (weather.toLowerCase()) {
            case "hot":
                healthEffect = -5; // Hot weather reduces health
                break;
            case "cold":
                healthEffect = -3; // Cold weather reduces health
                break;
            case "stormy":
                healthEffect = -8; // Stormy weather has severe health impact
                break;
            case "rainy":
                healthEffect = -2; // Rain has minor health impact
                break;
            case "foggy":
                healthEffect = -1; // Fog has very minor health impact
                break;
            case "clear":
                healthEffect = 1; // Clear weather slightly improves health
                break;
        }

        // Apply health effects to all alive players
        for (Player player : myplayers) {
            if (player.playerAlive()) {
                player.playerModifyHealth(healthEffect);
            }
        }
    }

    private void createCheckpointNamesBox() {
        checkpointNamesBox = new HBox();
        checkpointNamesBox.setAlignment(Pos.CENTER);
        checkpointNamesBox.setSpacing(0);
        // checkpointNamesBox.setPrefWidth(800);
        String[] names = mytrail.getLandmarks();
        for (String name : names) {
            Label label = new Label(name);
            label.setFont(Font.font("Courier New", 10));
            label.setTextFill(Color.LIGHTGRAY);
            label.setStyle("-fx-padding: 0 8 0 8;");
            checkpointNamesBox.getChildren().add(label);
        }
    }

    private void updateCheckpointNamesLayout() {
        // Center the checkpoint names box and space the names evenly
        double width = (mapPanel != null && mapPanel.getWidth() > 0) ? mapPanel.getWidth() : 800;
        checkpointNamesBox.setPrefWidth(width);
        checkpointNamesBox.setMaxWidth(width);
        checkpointNamesBox.setMinWidth(width);
        int n = checkpointNamesBox.getChildren().size();
        if (n > 1) {
            double spacing = (width - n * 60) / (n - 1); // 60 is approx label width, adjust as needed
            checkpointNamesBox.setSpacing(Math.max(0, spacing));
        } else {
            checkpointNamesBox.setSpacing(0);
        }
    }

    // Add method to check if current checkpoint is a fort
    private boolean isAtFort() {
        String name = mytrail.getCurrentLandmark().toLowerCase();
        return name.contains("fort");
    }

    // Add method to show fort arrival options
    private void showFortOptions(Stage stage, String fortName) {
        VBox fortLayout = new VBox(20);
        fortLayout.setStyle("-fx-padding: 40; -fx-background-color: #000000;");
        // fortLayout.setAlignment(Pos.CENTER);

        Label fortLabel = new Label("You have arrived at " + fortName + ".");
        fortLabel.setStyle("-fx-text-fill: #00FF00; -fx-font-family: 'Courier New'; -fx-font-size: 20px;");

        Button storeButton = new Button("Go to Store");
        storeButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
        storeButton.setOnAction(e -> {
            StorePanel store = new StorePanel(myinventory);
            store.show(stage, () -> play(stage));
        });

        Button continueButton = new Button("Continue on Trail");
        continueButton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #000000; -fx-font-family: 'Courier New'; -fx-font-size: 16px;");
        continueButton.setOnAction(e -> play(stage));

        fortLayout.getChildren().addAll(fortLabel, storeButton, continueButton);
        Scene fortScene = new Scene(fortLayout, stage.getWidth(), stage.getHeight(), Color.BLACK);
        stage.setScene(fortScene);
    }
}
