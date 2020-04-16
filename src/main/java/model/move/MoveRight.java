package main.java.model.move;

public class MoveRight extends Movements {
    public MoveRight(int startRow, int startCol, int squaresMoved) {
        destination[0] = startRow;
        destination[1] = startCol + squaresMoved;
    }

}
