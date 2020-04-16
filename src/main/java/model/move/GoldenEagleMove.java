package main.java.model.move;

import main.java.model.move.movements.MoveBehindPiece;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;
import java.util.List;

public class GoldenEagleMove {

    private List<Move> moveList;

    public GoldenEagleMove(int startRow, int startCol, List<int[]> piecePositionList) {
        moveList = new ArrayList<>();

        for (int[] positions : piecePositionList) {
            moveList.add(new Move(new MoveBehindPiece(startRow, startCol, positions[0], positions[1]),
                                  new TriangleShape(startRow, startCol, TriangleShape.DIRECTION_LEFT)));
        }

    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
