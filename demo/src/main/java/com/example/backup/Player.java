package com.example;

public class Player{
  public Player(){
    playerhealth=100;
  }
  public boolean playerAlive(){
    return playerhealth>0;
  }
  public int playerStatus(){
    return playerhealth;
  }
  public void playerModifyHealth(int amount){
    playerhealth+=amount;
    if (playerhealth < 0) playerhealth = 0;
    if (playerhealth > 100) playerhealth = 100;
  }
  private int playerhealth;
}