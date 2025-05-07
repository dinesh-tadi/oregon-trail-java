package com.example;

public class Inventory{
  public Inventory(){
    mycash=800;
    myfood=50;
    myammo=0;
    myclothing=0;
    myoxen=0;
    myaxel=0;
    mywheel=0;
    mytongue=0;
    mymedicines=10;
  }
  public int getCash(){
    return mycash;
  }
  public int getFood(){
    return myfood;
  }
  public int getAmmo(){
    return myammo;
  }
  public void consumeFood(int amount){
    myfood-=amount;
  }
  public void addFood(int amount){
    myfood+=amount;
  }
  public void consumeAmmo(int amount){
    myammo-=amount;
  }
  public void consumeMedicine(){
    if(mymedicines>0) mymedicines--;
  }
  public void banditsAttack(){
    mycash-=100;
    myfood-=10;
    mymedicines-=5;
    if(mycash<0) mycash=0;
    if(myfood<0) myfood=0;
    if(mymedicines<0) mymedicines=0;
  }
  public void displayResourcesleft(){
    System.out.println("Cash: "+mycash);
    System.out.println("Food: "+myfood);
    System.out.println("Ammo: "+myammo);
    System.out.println("Food: "+myfood);
    System.out.println("Clothing: "+myclothing);
    System.out.println("Oxen: "+myoxen);
    System.out.println("Axels: "+myaxel);
    System.out.println("Wheels: "+mywheel);
    System.out.println("Tongues: "+mytongue);
    System.out.println("Medicines: "+mymedicines);
  }
  private int mycash;
  private int myfood;
  private int myammo;
  private int myclothing;
  private int myoxen;
  private int myaxel;
  private int mywheel;
  private int mytongue;
  private int mymedicines;
}