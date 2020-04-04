package main.java.model.piece;

import main.java.model.move.GoblinSharkMove;
import main.java.model.move.Move;

import java.util.Collections;
import java.util.List;

public class GoblinShark extends Shark {
    // 0 for now
    private final int NUM_SQUARES_MOVED = 0;

    public GoblinShark() {
        super();
//        this.moveList = getMoveList();
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        Move move1 = movePiece(startRow, startCol, DIRECTION_DIAGONAL_RIGHT);
        Move move2 = movePiece(startRow, startCol, DIRECTION_DIAGONAL_LEFT);

        List<Move> moveList = getAllMovesList();
        Collections.addAll(moveList, move1, move2);
        return moveList;
    }

    private Move movePiece(int startRow, int startCol, String direction) {
        Move move = new GoblinSharkMove(startRow, startCol, NUM_SQUARES_MOVED, direction);
        return move;
    }
}

