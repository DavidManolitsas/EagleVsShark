package main.java.model;

import main.java.model.piece.Piece;

public class Square {
    private Piece piece;

    private Player occupiedPlayer;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Player getOccupiedPlayer() {
        return occupiedPlayer;
    }

    public void setOccupiedPlayer(Player occupiedPlayer) {
        this.occupiedPlayer = occupiedPlayer;
    }
}
