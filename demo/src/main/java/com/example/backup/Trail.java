package com.example;

public class Trail{
  public Trail(){
    mydistance=0;
    curCheckpoint=0;
  }
  public boolean theEnd(){
    return curCheckpoint>=10;
  }
  public void checkpoint(){
    if(!theEnd()){
      curCheckpoint++;
      System.out.println("Reached checkpoint "+curCheckpoint);
    }
  }

  private int mydistance;
  private int curCheckpoint;

}