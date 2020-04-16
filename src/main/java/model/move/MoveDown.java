package main.java.model.move;

public class MoveDown extends Movements {
    public MoveDown(int startRow, int startCol, int squaresMoved) {
        destination[0] = startRow + squaresMoved;
        destination[1] = startCol;
    }

}
