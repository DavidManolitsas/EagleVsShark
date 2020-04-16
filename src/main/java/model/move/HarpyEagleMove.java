package main.java.model.move;

import main.java.model.move.movements.*;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;
import java.util.List;

public class HarpyEagleMove {

    private List<Move> moveList;

    public HarpyEagleMove(int startRow, int startCol, int squaresMoved) {
        moveList = new ArrayList<>();

        Movements[] movements = {
                new MoveLeft(startRow, startCol, squaresMoved),
                new MoveRight(startRow, startCol, squaresMoved),
                new MoveDown(startRow, startCol, squaresMoved),
                new MoveUp(startRow, startCol, squaresMoved)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new TriangleShape(startRow, startCol, TriangleShape.DIRECTION_LEFT)));
        }
    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
