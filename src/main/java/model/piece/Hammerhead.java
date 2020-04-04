package main.java.model.piece;

import main.java.model.move.HammerheadMove;
import main.java.model.move.Move;

import java.util.List;

public class Hammerhead extends Shark {

    private final int NUM_SQUARES_MOVED = 3;

    public Hammerhead() {
        super();
//        this.moveList = getMoveList();
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {

        String[] directions = {DIRECTION_UP, DIRECTION_DIAGONAL_UP_RIGHT, DIRECTION_DIAGONAL_UP_LEFT};
        for (String direction : directions) {
            Move move = movePiece(startRow, startCol, direction);
            allMovesList.add(move);
        }

        return allMovesList;
    }


    private Move movePiece(int startRow, int startCol, String direction) {
        Move move = new HammerheadMove(startCol, startRow, NUM_SQUARES_MOVED, direction);
        return move;
    }
}
