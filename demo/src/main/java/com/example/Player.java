package com.example;

public class Player {
    private String name;
    private int playerhealth;
    private int strength;
    private int intelligence;
    private int charisma;
    private int[] relationships; // Relationships with other players
    private String role;
    private boolean isLeader;

    public Player() {
        playerhealth = 100;
        strength = 50;
        intelligence = 50;
        charisma = 50;
        relationships = new int[5]; // 5 players max
        role = "Traveler";
        isLeader = false;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public boolean playerAlive() {
        return playerhealth > 0;
    }

    public int playerStatus() {
        return playerhealth;
    }

    public void playerModifyHealth(int amount) {
        playerhealth += amount;
        if (playerhealth < 0) playerhealth = 0;
        if (playerhealth > 100) playerhealth = 100;
    }

    public void setRole(String newRole) {
        role = newRole;
        // Adjust stats based on role
        switch (role.toLowerCase()) {
            case "farmer":
                strength += 10;
                break;
            case "carpenter":
                intelligence += 10;
                break;
            case "banker":
                charisma += 10;
                break;
        }
    }

    public String getRole() {
        return role;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
        if (leader) charisma += 5;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void modifyRelationship(int playerIndex, int amount) {
        if (playerIndex >= 0 && playerIndex < relationships.length) {
            relationships[playerIndex] += amount;
            // Keep relationships between -100 and 100
            if (relationships[playerIndex] < -100) relationships[playerIndex] = -100;
            if (relationships[playerIndex] > 100) relationships[playerIndex] = 100;
        }
    }

    public int getRelationship(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < relationships.length) {
            return relationships[playerIndex];
        }
        return 0;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getCharisma() {
        return charisma;
    }

    public void modifyStrength(int amount) {
        strength += amount;
        if (strength < 0) strength = 0;
        if (strength > 100) strength = 100;
    }

    public void modifyIntelligence(int amount) {
        intelligence += amount;
        if (intelligence < 0) intelligence = 0;
        if (intelligence > 100) intelligence = 100;
    }

    public void modifyCharisma(int amount) {
        charisma += amount;
        if (charisma < 0) charisma = 0;
        if (charisma > 100) charisma = 100;
    }
}