package main.java.model.move;

import main.java.model.move.movements.*;
import main.java.model.move.shape.SquareShape;

import java.util.ArrayList;
import java.util.List;

public class GoblinSharkMove extends MoveFactory{

    private static final int SQUARE_MOVED = 1;

    public GoblinSharkMove(int startRow, int startCol) {

        Movements[] movements = {
                new MoveLeft(startRow, startCol, SQUARE_MOVED),
                new MoveRight(startRow, startCol, SQUARE_MOVED),
                new MoveDiagonalLeft(startRow, startCol, SQUARE_MOVED),
                new MoveDiagonalRight(startRow, startCol, SQUARE_MOVED)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new SquareShape(movement.getDestination())));
        }
    }
}
