package com.example;

import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Events {
    private static Random random = new Random();

    public static void generateRandomEvent(Player[] myplayers, Inventory myinventory) {
        int eventType = random.nextInt(12); // Increased number of possible events
        String eventMessage = "";
        boolean requiresChoice = false;
        String[] choices = new String[0];

        switch (eventType) {
            case 0: // Storm
                eventMessage = "A violent storm approaches!";
                choices = new String[]{"Take shelter (lose 1 day)", "Continue through storm"};
                requiresChoice = true;
                break;
            case 1: // Found food
                eventMessage = "You found a small cache of supplies!";
                myinventory.addFood(5);
                break;
            case 2: // Bandits
                eventMessage = "Bandits approach!";
                choices = new String[]{"Fight", "Pay them off", "Try to flee"};
                requiresChoice = true;
                break;
            case 3: // Good progress
                eventMessage = "The weather is perfect and you make excellent progress!";
                for (int i = 0; i < myplayers.length; i++) {
                    if (myplayers[i].playerAlive()) {
                        myplayers[i].playerModifyHealth(20);
                    }
                }
                break;
            case 4: // Sickness
                int person = random.nextInt(myplayers.length);
                eventMessage = "Player " + (person + 1) + " has fallen ill!";
                choices = new String[]{"Use medicine", "Rest for a day", "Continue traveling"};
                requiresChoice = true;
                break;
            case 5: // Lost in fog
                eventMessage = "You've lost your way in a thick fog!";
                choices = new String[]{"Wait for fog to clear", "Try to find the trail"};
                requiresChoice = true;
                break;
            case 6: // River crossing
                eventMessage = "You've reached a river crossing!";
                choices = new String[]{"Ford the river", "Wait for ferry", "Build a raft"};
                requiresChoice = true;
                break;
            case 7: // Trading opportunity
                eventMessage = "A group of traders approaches!";
                choices = new String[]{"Trade supplies", "Ignore them", "Rob them"};
                requiresChoice = true;
                break;
            case 8: // Hunting opportunity
                eventMessage = "You spot game in the distance!";
                choices = new String[]{"Hunt", "Continue traveling"};
                requiresChoice = true;
                break;
            case 9: // Wagon damage
                eventMessage = "Your wagon has sustained damage!";
                choices = new String[]{"Repair now", "Continue with damage"};
                requiresChoice = true;
                break;
            case 10: // Weather change
                eventMessage = "The weather is changing rapidly!";
                choices = new String[]{"Set up camp", "Continue traveling"};
                requiresChoice = true;
                break;
            case 11: // Native encounter
                eventMessage = "You encounter a group of Native Americans!";
                choices = new String[]{"Trade", "Ask for guidance", "Avoid them"};
                requiresChoice = true;
                break;
        }

        if (requiresChoice) {
            handleEventChoice(eventType, choices, myplayers, myinventory);
        } else {
            System.out.println(eventMessage);
        }
    }

    private static void handleEventChoice(int eventType, String[] choices, Player[] myplayers, Inventory myinventory) {
        // In a real implementation, this would show a dialog with the choices
        // For now, we'll randomly choose an option
        int choice = random.nextInt(choices.length);
        String selectedChoice = choices[choice];
        
        switch (eventType) {
            case 0: // Storm
                if (selectedChoice.equals("Take shelter (lose 1 day)")) {
                    System.out.println("You take shelter and wait out the storm.");
                } else {
                    System.out.println("You brave the storm!");
                    for (Player player : myplayers) {
                        if (player.playerAlive()) {
                            player.playerModifyHealth(-30);
                        }
                    }
                }
                break;
            case 2: // Bandits
                if (selectedChoice.equals("Fight")) {
                    if (myinventory.getAmmo() > 0) {
                        System.out.println("You successfully fight off the bandits!");
                        myinventory.consumeAmmo(5);
                    } else {
                        System.out.println("You have no ammunition! The bandits take your supplies.");
                        myinventory.banditsAttack();
                    }
                } else if (selectedChoice.equals("Pay them off")) {
                    System.out.println("You pay the bandits to leave you alone.");
                    myinventory.removeCash(50);
                } else {
                    System.out.println("You attempt to flee!");
                    if (random.nextBoolean()) {
                        System.out.println("You successfully escape!");
                    } else {
                        System.out.println("The bandits catch you and take your supplies!");
                        myinventory.banditsAttack();
                    }
                }
                break;
            // Add more event handling cases here
        }
    }
}
