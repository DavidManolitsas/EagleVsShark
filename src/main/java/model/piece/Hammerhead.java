package main.java.model.piece;

import main.java.model.move.HammerheadMove;
import main.java.model.move.Move;

import java.util.List;


public class Hammerhead
        extends Shark {
    private final int NUM_SQUARES_MOVED = 1;
    private String[] directions = {DIRECTION_UP, DIRECTION_DIAGONAL_UP_RIGHT, DIRECTION_DIAGONAL_UP_LEFT};

    public Hammerhead(int startRow, int startCol) {
        super(startRow, startCol);
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        for (String direction : directions) {
            Move move = new HammerheadMove(startRow, startCol, NUM_SQUARES_MOVED, direction);
            allMovesList.add(move);
        }
        return allMovesList;
    }

}
