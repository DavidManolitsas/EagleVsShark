package main.java.model.move;

import main.java.model.move.movements.*;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;
import java.util.List;

public class HarpyEagleMove {

    private static final int SQUARE_MOVED = 3;

    private List<Move> moveList;

    public HarpyEagleMove(int startRow, int startCol) {
        moveList = new ArrayList<>();

        Movements[] movements = {
                new MoveLeft(startRow, startCol, SQUARE_MOVED),
                new MoveRight(startRow, startCol, SQUARE_MOVED),
                new MoveDown(startRow, startCol, SQUARE_MOVED),
                new MoveUp(startRow, startCol, SQUARE_MOVED)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new TriangleShape(startRow, startCol, TriangleShape.DIRECTION_LEFT)));
        }
    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
