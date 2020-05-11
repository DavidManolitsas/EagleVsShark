package main.java.model.player;

public abstract class Player {
    private String name;
    private boolean isUndo;

    public Player(String name) {
        this.name = name;
    }

    public String getPlayerName() {
        return name;
    }

    public boolean isUndo() {
        return isUndo;
    }

    public void setUndo() {
        isUndo = true;
    }
}
