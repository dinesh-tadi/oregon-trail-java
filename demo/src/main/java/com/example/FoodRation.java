package com.example;

public class FoodRation {
    public enum RationLevel {
        STARVING(0.5, -10),    // 0.5 lbs per person, -10 health per day
        SCARCE(1.0, -5),      // 1.0 lbs per person, -5 health per day
        DECENT(2.0, 0),       // 2.0 lbs per person, no health change
        HEALTHY(3.0, 5);      // 3.0 lbs per person, +5 health per day

        private final double poundsPerPerson;
        private final int healthEffect;

        RationLevel(double poundsPerPerson, int healthEffect) {
            this.poundsPerPerson = poundsPerPerson;
            this.healthEffect = healthEffect;
        }

        public double getPoundsPerPerson() {
            return poundsPerPerson;
        }

        public int getHealthEffect() {
            return healthEffect;
        }
    }

    private RationLevel currentRation;
    private int numPlayers;

    public FoodRation(int numPlayers) {
        this.numPlayers = numPlayers;
        this.currentRation = RationLevel.DECENT; // Start with decent rations
    }

    public void setRationLevel(RationLevel level) {
        this.currentRation = level;
    }

    public RationLevel getCurrentRation() {
        return currentRation;
    }

    public double getDailyFoodConsumption() {
        return currentRation.getPoundsPerPerson() * numPlayers;
    }

    public int getDailyHealthEffect() {
        return currentRation.getHealthEffect();
    }

    public String getRationDescription() {
        switch (currentRation) {
            case STARVING:
                return "Starving - " + getDailyFoodConsumption() + " lbs per day";
            case SCARCE:
                return "Scarce - " + getDailyFoodConsumption() + " lbs per day";
            case DECENT:
                return "Decent - " + getDailyFoodConsumption() + " lbs per day";
            case HEALTHY:
                return "Healthy - " + getDailyFoodConsumption() + " lbs per day";
            default:
                return "Unknown ration level";
        }
    }
} 