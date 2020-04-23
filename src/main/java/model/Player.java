package main.java.model;

public abstract class Player {
    private String name;

    public Player(String name){
        this.name = name;
    }


    public String getPlayerName(){
        return name;
    }
}
