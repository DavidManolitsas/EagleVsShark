package main.java.model.move.movements;

public class MoveBehindPiece
        extends Movements {
    public MoveBehindPiece(int startRow, int startCol, int pieceRow, int pieceCol) {
        super(startRow, startCol, 0);
        destination[0] = pieceRow + 1;
        destination[1] = pieceCol;
    }

    @Override
    public String toString() {
        return String.format("Move to (%1d, %2d)", destination[0], destination[1]);
    }
}