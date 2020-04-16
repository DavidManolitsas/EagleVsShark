package main.java.model.move;

public class MoveLeft extends Movements {
    public MoveLeft(int startRow, int startCol, int squaresMoved) {
        destination[0] = startRow;
        destination[1] = startCol - squaresMoved;
    }
}
