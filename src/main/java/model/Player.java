package main.java.model;

public class Player {
    private String name;
    private int score;

    public Player(String name){
        this.name = name;
        score = 0;
    }

    public String getPlayerName(){
        return name;
    }
}
