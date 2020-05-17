package main.java.model.piece;

import main.java.model.move.HarpyEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class HarpyEagle
        extends Eagle {

    public HarpyEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Ensures:
     * moveList of HarpyEagleMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol, boolean isPowered) {
        return new HarpyEagleMove(startRow, startCol, isPowered).getMoveList();
    }
}
