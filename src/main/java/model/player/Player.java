package main.java.model.player;

public abstract class Player {
    private String name;

    public Player(String name){
        this.name = name;
    }


    public String getPlayerName(){
        return name;
    }
}
