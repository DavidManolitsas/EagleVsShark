package main.java.model.piece;


import main.java.model.move.BaldEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class BaldEagle
        extends Eagle {

    public BaldEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Ensures:
     * moveList of BaldEagleMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol, boolean isPowered) {
        return new BaldEagleMove(startRow, startCol, isPowered).getMoveList();
    }
}
