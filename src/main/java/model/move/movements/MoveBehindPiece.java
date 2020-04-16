package main.java.model.move.movements;

public class MoveBehindPiece
        extends Movements {
    public MoveBehindPiece(int startRow, int startCol, int pieceRow, int pieceCol) {
        super(startRow, startCol);
        destination[0] = pieceRow + 1;
        destination[1] = pieceCol;
    }
}