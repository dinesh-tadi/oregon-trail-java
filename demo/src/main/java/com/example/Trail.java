package com.example;

import java.util.Random;

public class Trail {
    private int mydistance;
    private int curCheckpoint;
    private int numdays;
    private String currentWeather;
    private String[] landmarks = {
        "Independence, Missouri",
        "Kansas River Crossing",
        "Big Blue River Crossing",
        "Fort Kearney",
        "Chimney Rock",
        "Fort Laramie",
        "Independence Rock",
        "South Pass",
        "Fort Bridger",
        "Fort Hall",
        "Snake River Crossing",
        "Fort Boise",
        "Blue Mountains",
        "Fort Walla Walla",
        "The Dalles",
        "Oregon City"
    };
    private String[] weatherTypes = {"Clear", "Rainy", "Stormy", "Foggy", "Hot", "Cold"};
    private Random random;

    public Trail() {
        mydistance = 0;
        curCheckpoint = 0;
        numdays = 0;
        random = new Random();
        currentWeather = "Clear"; // Set default weather
        updateWeather(); // This will potentially change the default weather
    }

    public boolean theEnd() {
        return curCheckpoint >= landmarks.length - 1;
    }

    public void checkpoint() {
        if (!theEnd()) {
            curCheckpoint++;
            mydistance += 100; // Each checkpoint is roughly 100 miles
            numdays += 5; // Each checkpoint takes roughly 5 days
            updateWeather();
            System.out.println("Reached " + landmarks[curCheckpoint] + " (Checkpoint " + curCheckpoint + ")");
            System.out.println("Total distance traveled: " + mydistance + " miles");
            System.out.println("Current weather: " + currentWeather);
        }
    }

    public void updateWeather() {
        // 70% chance to keep current weather, 30% chance to change
        if (random.nextDouble() < 0.3) {
            currentWeather = weatherTypes[random.nextInt(weatherTypes.length)];
        }
    }

    public String getCurrentWeather() {
        return currentWeather != null ? currentWeather : "Clear"; // Return default if null
    }

    public String getCurrentLandmark() {
        return landmarks[curCheckpoint];
    }

    public String getNextLandmark() {
        if (curCheckpoint + 1 < landmarks.length) {
            return landmarks[curCheckpoint + 1];
        }
        return "Final Destination";
    }

    public int getDistance() {
        return mydistance;
    }

    public int getDaysTraveled() {
        return numdays;
    }

    public boolean isRiverCrossing() {
        return landmarks[curCheckpoint].contains("River");
    }

    public int getDistanceToNextCheckpoint() {
        return 100; // Each checkpoint is roughly 100 miles
    }

    public String[] getLandmarks() {
        return landmarks;
    }

    public int getCurrentCheckpoint() {
        return curCheckpoint;
    }
}