package main.java.model.move;

import main.java.model.piece.Piece;

public class CustomPieceMove {
    private Piece piece;
    private int[] previousLocation;
    private int[] newLocation;

    public CustomPieceMove(Piece piece, int[] previousLocation, int[] newLocation) {
        this.piece = piece;
        this.previousLocation = previousLocation;
        this.newLocation = newLocation;
    }

    public Piece getPiece() {
        return piece;
    }

    public int[] getPreviousLocation() {
        return previousLocation;
    }

    public int[] getCurrentLocation() {
        return newLocation;
    }
}
