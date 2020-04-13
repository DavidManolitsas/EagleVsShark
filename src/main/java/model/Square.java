package main.java.model;

import main.java.model.piece.Piece;

public class Square {

    private int row;

    private int col;

    private Piece piece;

    private Player occupiedPlayer;

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
