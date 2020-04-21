package main.java.model.move;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;

public class SawSharkMove extends PieceMove {

    private static final int SQUARE_MOVED = 2;

    public SawSharkMove(int startRow, int startCol) {
        moveList = new ArrayList<>();
        moveList.add(new Move(new MoveDiagonalLeft(startRow, startCol, SQUARE_MOVED),
                new TriangleShape(startRow, startCol, TriangleShape.DIRECTION_LEFT)));
        moveList.add(new Move(new MoveDiagonalRight(startRow, startCol, SQUARE_MOVED),
                new TriangleShape(startRow, startCol, TriangleShape.DIRECTION_RIGHT)));
    }
}
