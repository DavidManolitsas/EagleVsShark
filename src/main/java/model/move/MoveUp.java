package main.java.model.move;

public class MoveUp extends Movements {
    public MoveUp(int startRow, int startCol, int squaresMoved) {
        destination[0] = startRow - squaresMoved;
        destination[1] = startCol;
    }

}