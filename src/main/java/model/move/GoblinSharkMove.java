package main.java.model.move;

import main.java.model.move.movements.*;
import main.java.model.move.shape.SquareShape;

import java.util.ArrayList;
import java.util.List;

public class GoblinSharkMove {

    private List<Move> moveList;

    public GoblinSharkMove(int startRow, int startCol, int squaresMoved) {
        moveList = new ArrayList<>();

        Movements[] movements = {
                new MoveLeft(startRow, startCol, squaresMoved),
                new MoveRight(startRow, startCol, squaresMoved),
                new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                new MoveDiagonalRight(startRow, startCol, squaresMoved)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new SquareShape(movement.getDestination())));
        }
    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
