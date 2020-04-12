package main.java.model.piece;

import main.java.model.move.GoblinSharkMove;
import main.java.model.move.Move;

import java.util.List;


public class GoblinShark
        extends Shark {

    private final int NUM_SQUARES_MOVED = 1;
    // still unsure about movement
    private String[] directions = {DIRECTION_LEFT, DIRECTION_DIAGONAL_UP_LEFT, DIRECTION_DIAGONAL_UP_RIGHT, DIRECTION_RIGHT};

    public GoblinShark(int startRow, int startCol) {
        super(startRow, startCol);
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        for (String direction : directions) {
            Move move = new GoblinSharkMove(startRow, startCol, NUM_SQUARES_MOVED, direction);
            allMovesList.add(move);
        }

        return allMovesList;
    }

}

