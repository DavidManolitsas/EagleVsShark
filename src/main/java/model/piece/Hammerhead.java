package main.java.model.piece;

import main.java.model.move.HammerheadMove;
import main.java.model.move.Move;

import java.util.Collections;
import java.util.List;

public class Hammerhead extends Shark {

    private final int NUM_SQUARES_MOVED = 3;

    public Hammerhead() {
        super();
//        this.moveList = getMoveList();
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        Move move1 = movePiece(startRow, startCol, DIRECTION_UP);
        Move move2 = movePiece(startRow, startCol, DIRECTION_DIAGONAL_RIGHT);
        Move move3 = movePiece(startRow, startCol, DIRECTION_DIAGONAL_LEFT);

        List<Move> moveList = getAllMovesList();
        Collections.addAll(moveList, move1, move2, move3);
        return moveList;
    }

    // if piece is on [8,8], it can move from:
    //
    public Move movePiece(int startRow, int startCol, String direction) {
        Move move = new HammerheadMove(startCol, startRow, NUM_SQUARES_MOVED, direction);
        return move;
    }
}
