package main.java.model.move;

import main.java.model.move.movements.MoveBehindPiece;
import main.java.model.move.shape.CrossShape;

import java.util.List;

public class GoldenEagleMove extends PieceMove {

    public GoldenEagleMove(int startRow, int startCol, List<int[]> piecePositionList) {
        MoveBehindPiece move;

        for (int[] positions : piecePositionList) {
            move = new MoveBehindPiece(startRow, startCol, positions[0], positions[1]);
            moveList.add(new Move(move, new CrossShape(move.getDestination())));
        }

    }
}
