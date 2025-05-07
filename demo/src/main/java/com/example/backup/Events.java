package com.example;

import java.util.Random;

public class Events{
    public static void generateRandomEvent(Player myplayers[], Inventory myinventory) {
        Random random = new Random();
        int eventType = random.nextInt(8);

        switch (eventType) {
            case 0:
                System.out.println("You encountered a storm!");
                for(int i=0;i<5;i++) myplayers[i].playerModifyHealth(-20);
                break;
            case 1:
                System.out.println("You found extra food!");
                myinventory.addFood(5);
                break;
            case 2:
                System.out.println("A bandit stole some supplies!");
                myinventory.banditsAttack();
                break;
            case 3:
                System.out.println("You are healthy and made good progress.");
                for(int i=0;i<5;i++) myplayers[i].playerModifyHealth(20);
                break;
            case 4:
                int person = random.nextInt(5);
                System.out.println("Player "+(person+1)+" fell sick!");
                myplayers[person].playerModifyHealth(-25);
                break;
        }
    }
}
