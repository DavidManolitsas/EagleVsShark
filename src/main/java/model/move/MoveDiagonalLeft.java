package main.java.model.move;

public class MoveDiagonalLeft extends Movements {
    public MoveDiagonalLeft(int startRow, int startCol, int squaresMoved) {
        destination[0] = startRow - squaresMoved;
        destination[1] = startCol - squaresMoved;
    }
}
