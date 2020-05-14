package main.java.model.piece;

import java.util.List;

import main.java.model.move.HarpyEagleMove;
import main.java.model.move.Move;

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
    public List<Move> getAllMoves(int startRow, int startCol) {
        setPieceMove(new HarpyEagleMove(startRow, startCol, 3, false));
        return getPieceMove().getMoveList();
    }

    @Override
    public List<Move> getAllPowerMoves(int startRow, int startCol) {
        setPieceMove(new HarpyEagleMove(startRow, startCol, 1, true));
        return getPieceMove().getMoveList();
    }

}
