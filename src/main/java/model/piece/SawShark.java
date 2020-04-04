package main.java.model.piece;

import main.java.model.move.Move;
import main.java.model.move.SawSharkMove;

import java.util.List;

public class SawShark extends Shark {

    private final int NUM_SQUARES_MOVED = 2;

    public SawShark() {
        super();

    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {

        String[] directions = {DIRECTION_DIAGONAL_UP_RIGHT, DIRECTION_DIAGONAL_UP_LEFT};
        for (String direction : directions) {
            Move move = movePiece(startRow, startCol, direction);
            allMovesList.add(move);
        }

        return allMovesList;

    }

    private Move movePiece(int startRow, int startCol, String direction) {
        Move move = new SawSharkMove(startRow, startCol, NUM_SQUARES_MOVED, direction);
        return move;
    }
}
