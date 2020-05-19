package main.java.model;

public enum Player {
    EAGLE("Eagle", 0),
    SHARK("Shark", 0);

    private String name;
    private int remainingPowerMoves;

    Player(String name, int remainingPowerMoves) {
        this.name = name;
        this.remainingPowerMoves = remainingPowerMoves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingPowerMoves() {
        return remainingPowerMoves;
    }

    public void setRemainingPowerMoves(int remainingPowerMoves) {
        this.remainingPowerMoves = remainingPowerMoves;
    }
}
