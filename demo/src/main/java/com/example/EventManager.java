package com.example;

import java.util.Random;

import javafx.stage.Stage;

public class EventManager {
    private static Random random = new Random();

    public static void triggerRandomEvent(Player[] players, Inventory inventory, Stage stage, Runnable onContinue) {
        int eventType = random.nextInt(5); // Expand as needed
        switch (eventType) {
            case 0:
                showIllnessEvent(players, inventory, stage, onContinue);
                break;
            case 1:
                showHuntingEvent(players, inventory, stage, onContinue);
                break;
            case 2:
                showRiverCrossingEvent(players, inventory, stage, onContinue);
                break;
            case 3:
                showTradingEvent(players, inventory, stage, onContinue);
                break;
            default:
                onContinue.run();
        }
    }

    public static void showIllnessEvent(Player[] players, Inventory inventory, Stage stage, Runnable onContinue) {
        int person = random.nextInt(players.length);
        Player p = players[person];
        if (!p.playerAlive()) { onContinue.run(); return; }
        p.playerModifyHealth(-random.nextInt(30) - 20); // Drastic health drop
        if (p.playerStatus() <= 0) {
            // Mark as dead
            // Optionally show a dialog
        }
        onContinue.run();
    }

    public static void showHuntingEvent(Player[] players, Inventory inventory, Stage stage, Runnable onContinue) {
        // Simple hunting: use ammo, gain food, or fail
        if (inventory.getAmmo() < 5) {
            // Not enough ammo
            onContinue.run();
            return;
        }
        inventory.consumeAmmo(5);
        if (random.nextBoolean()) {
            inventory.addFood(30 + random.nextInt(40));
        }
        onContinue.run();
    }

    public static void showRiverCrossingEvent(Player[] players, Inventory inventory, Stage stage, Runnable onContinue) {
        // Present choices: ford, ferry, raft (to be implemented with UI)
        // For now, random outcome
        int outcome = random.nextInt(3);
        if (outcome == 0) {
            // Success
        } else {
            // Lose supplies or health
            for (Player p : players) if (p.playerAlive()) p.playerModifyHealth(-random.nextInt(20));
            inventory.removeCash(20);
        }
        onContinue.run();
    }

    public static void showTradingEvent(Player[] players, Inventory inventory, Stage stage, Runnable onContinue) {
        // Random trade: gain or lose supplies
        int tradeType = random.nextInt(2);
        if (tradeType == 0) {
            inventory.addFood(10);
        } else {
            inventory.removeCash(10);
        }
        onContinue.run();
    }
} 