package main.java.model.move;

public class MoveDiagonalRight extends Movements {
    public MoveDiagonalRight(int startRow, int startCol, int squaresMoved) {
        destination[0] = startRow - squaresMoved;
        destination[1] = startCol + squaresMoved;
    }
}
