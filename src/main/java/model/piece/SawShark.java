package main.java.model.piece;

import java.util.List;

import main.java.model.move.Move;
import main.java.model.move.SawSharkMove;

public class SawShark
        extends Shark {

    public SawShark(int startRow, int startCol) {
        super(startRow, startCol);
    }


    /**
     * Ensures:
     * moveList of SawSharkMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        setPieceMove(new SawSharkMove(startRow, startCol, 3, 2));
        return getPieceMove().getMoveList();
    }


    @Override
    public List<Move> getAllPowerMoves(int startRow, int startCol) {
        setPieceMove(new SawSharkMove(startRow, startCol, 4, 3));
        return getPieceMove().getMoveList();
    }

}
