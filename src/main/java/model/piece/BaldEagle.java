package main.java.model.piece;


import java.util.List;

import main.java.model.move.BaldEagleMove;
import main.java.model.move.Move;

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
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new BaldEagleMove(startRow, startCol, 1).getMoveList();
    }

    @Override
    public List<Move> getAllPowerMoves(int startRow, int startCol) {
        return new BaldEagleMove(startRow, startCol, 3).getMoveList();
    }

}
