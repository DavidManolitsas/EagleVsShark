package main.java.model.move.movements;

public class MoveRight
        extends Movements {
    public MoveRight(int startRow, int startCol, int squaresMoved) {
        super(startRow, startCol, squaresMoved);
        destination[0] = startRow;
        destination[1] = startCol + squaresMoved;
    }
}
