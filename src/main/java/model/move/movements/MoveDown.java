package main.java.model.move.movements;

public class MoveDown
        extends Movements {
    public MoveDown(int startRow, int startCol, int squaresMoved) {
        super(startRow, startCol, squaresMoved);
        destination[0] = startRow + squaresMoved;
        destination[1] = startCol;
    }
}
