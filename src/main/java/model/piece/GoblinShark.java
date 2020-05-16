package main.java.model.piece;

import main.java.model.move.GoblinSharkMove;
import main.java.model.move.Move;

import java.util.List;


public class GoblinShark
        extends Shark {

    public GoblinShark(int startRow, int startCol) {
        super(startRow, startCol);
    }


    /**
     * Ensures:
     * moveList of GoblinSharkMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol, boolean isPowered) {
        return new GoblinSharkMove(startRow, startCol, isPowered).getMoveList();
    }
}

