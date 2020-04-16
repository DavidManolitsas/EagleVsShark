package main.java.model.move;

import main.java.model.move.movements.MoveDown;
import main.java.model.move.movements.MoveLeft;
import main.java.model.move.movements.MoveRight;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.SquareShape;

import java.util.ArrayList;
import java.util.List;

public class BaldEagleMove {

    private static final int SQUARE_MOVED_ONE = 1;
    private static final int SQUARE_MOVED_TWO = 2;
    private static final int SQUARE_MOVED_THREE = 3;

    private List<Move> moveList;

    public BaldEagleMove(int startRow, int startCol) {
        moveList = new ArrayList<>();

        Movements[] movements = {
                new MoveLeft(startRow, startCol, SQUARE_MOVED_ONE),
                new MoveLeft(startRow, startCol, SQUARE_MOVED_TWO),
                new MoveLeft(startRow, startCol, SQUARE_MOVED_THREE),
                new MoveRight(startRow, startCol, SQUARE_MOVED_ONE),
                new MoveRight(startRow, startCol, SQUARE_MOVED_TWO),
                new MoveRight(startRow, startCol, SQUARE_MOVED_THREE),
                new MoveDown(startRow, startCol, SQUARE_MOVED_ONE),
                new MoveDown(startRow, startCol, SQUARE_MOVED_TWO),
                new MoveDown(startRow, startCol, SQUARE_MOVED_THREE)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new SquareShape(movement.getDestination())));
        }
    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
