package main.java.model;

public enum Player {
    EAGLE("Eagle", 0, 0),
    SHARK("Shark", 0, 0);

    private String name;
    private int remainingPowerMoves;
    private int undoMoves;

    Player(String name, int remainingPowerMoves, int undoMoves) {
        this.name = name;
        this.remainingPowerMoves = remainingPowerMoves;
        this.undoMoves = undoMoves;
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

    public int getUndoMoves() {
        return undoMoves;
    }

    public void setUndoMoves(int undoMoves) {
        this.undoMoves = undoMoves;
    }
}
