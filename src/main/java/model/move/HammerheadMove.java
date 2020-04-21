package main.java.model.move;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.movements.MoveUp;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.TShape;

import java.util.ArrayList;
import java.util.List;

public class HammerheadMove extends MoveFactory{

    private static final int SQUARE_MOVED = 1;

    public HammerheadMove(int startRow, int startCol) {

        Movements[] movements = {
                new MoveUp(startRow, startCol, SQUARE_MOVED),
                new MoveDiagonalLeft(startRow, startCol, SQUARE_MOVED),
                new MoveDiagonalRight(startRow, startCol, SQUARE_MOVED)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new TShape(movement.getDestination())));
        }
    }

}
