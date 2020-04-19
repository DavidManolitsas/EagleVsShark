package main.java.model.move;

import main.java.model.move.movements.MoveBehindPiece;
import main.java.model.move.shape.CrossShape;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;
import java.util.List;

public class GoldenEagleMove {

    private List<Move> moveList;

    public GoldenEagleMove(int startRow, int startCol, List<int[]> piecePositionList) {
        moveList = new ArrayList<>();
        MoveBehindPiece move;

        for (int[] positions : piecePositionList) {
            move = new MoveBehindPiece(startRow, startCol, positions[0], positions[1]);
            moveList.add(new Move(move, new CrossShape(move.getDestination())));
        }

    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
