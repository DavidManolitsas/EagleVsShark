package main.java.model;

import main.java.model.piece.Piece;
import main.java.model.player.Player;

/**
 * Invariant:
 * 1. row >= 0 && col >= 0
 * 2. row < Board.ROW && col < Board.COL
 */
public class Square {

    private int row;

    private int col;

    private Piece piece;

    private Player occupiedPlayer;

    /**
     * Requires:
     * 1. row >= 0 && col >= 0
     * 2. row < Board.ROW && col < Board.COL
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

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
