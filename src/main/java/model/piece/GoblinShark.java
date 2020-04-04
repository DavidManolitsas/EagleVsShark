package main.java.model.piece;

import main.java.model.move.GoblinSharkMove;
import main.java.model.move.Move;

import java.util.List;

public class GoblinShark extends Shark {
    // 0 for now
    private final int NUM_SQUARES_MOVED = 0;
    // still unsure about movement
    private String[] directions = {DIRECTION_DIAGONAL_UP_RIGHT, DIRECTION_DIAGONAL_UP_LEFT};

    public GoblinShark() {
        super();
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

