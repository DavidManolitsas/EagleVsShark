package main.java.model.move;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;
import java.util.List;

public class SawSharkMove {

    private List<Move> moveList;

    public SawSharkMove(int startRow, int startCol, int squaresMoved) {
        moveList = new ArrayList<>();
        moveList.add(new Move(new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, TriangleShape.DIRECTION_LEFT)));
        moveList.add(new Move(new MoveDiagonalRight(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, TriangleShape.DIRECTION_RIGHT)));
    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
