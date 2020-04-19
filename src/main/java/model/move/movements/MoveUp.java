package main.java.model.move.movements;

public class MoveUp
        extends Movements {
    public MoveUp(int startRow, int startCol, int squaresMoved) {
        super(startRow, startCol, squaresMoved);
        destination[0] = startRow - squaresMoved;
        destination[1] = startCol;
    }
}