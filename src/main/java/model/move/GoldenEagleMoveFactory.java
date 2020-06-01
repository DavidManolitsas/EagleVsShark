package main.java.model.move;

import main.java.model.board.Board;
import main.java.model.move.movements.MoveBehindPiece;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.CrossShape;

import java.util.ArrayList;
import java.util.List;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class GoldenEagleMoveFactory
        implements MoveFactory {

    @Override
    public List<Move> generateMoves(int startRow, int startCol, boolean isPowered, Board board) {
        List<Move> moveList = new ArrayList<>();

        Movements move;

        for (int[] positions : board.getSharksPositions()) {
            move = new MoveBehindPiece(startRow, startCol, positions[0], positions[1]);
            moveList.add(new Move(move, new CrossShape(move.getDestination(), isPowered)));
        }

        return moveList;
    }
}
