package main.java.model.piece;

import java.util.List;

import main.java.model.move.GoblinSharkMove;
import main.java.model.move.Move;


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
    public List<Move> getAllMoves(int startRow, int startCol) {
        setPieceMove(new GoblinSharkMove(startRow, startCol, 1, false));
        return getPieceMove().getMoveList();
    }

    @Override
    public List<Move> getAllPowerMoves(int startRow, int startCol) {
        setPieceMove(new GoblinSharkMove(startRow, startCol, 2, true));
        return getPieceMove().getMoveList();
    }
}

