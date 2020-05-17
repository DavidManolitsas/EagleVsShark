package main.java.model.player;

public abstract class Player {
    private String name;
    private int remainingPowerMoves;

    public Player(String name, int remainingPowerMoves) {
        this.name = name;
        this.remainingPowerMoves = remainingPowerMoves;
    }

    public String getPlayerName(){
        return name;
    }

    public int getRemainingPowerMoves() {
        return remainingPowerMoves;
    }

    public void setRemainingPowerMoves(int remainingPowerMoves) {
        this.remainingPowerMoves = remainingPowerMoves;
    }
}
