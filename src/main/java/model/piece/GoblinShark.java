package main.java.model.piece;

import main.java.model.move.GoblinSharkMove;
import main.java.model.move.Move;

import java.util.List;


public class GoblinShark extends Shark {

    private final int NUM_SQUARES_MOVED = 3;
    // still unsure about movement
    private String[] directions = {DIRECTION_UP};

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

