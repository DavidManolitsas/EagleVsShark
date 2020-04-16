package main.java.model.move.movements;

public class MoveDiagonalLeft
        extends Movements {
    public MoveDiagonalLeft(int startRow, int startCol, int squaresMoved) {
        super(startRow, startCol);
        destination[0] = startRow - squaresMoved;
        destination[1] = startCol - squaresMoved;
    }
}
