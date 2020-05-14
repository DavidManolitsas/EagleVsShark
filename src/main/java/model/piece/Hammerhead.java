package main.java.model.piece;

import java.util.List;

import main.java.model.move.HammerheadMove;
import main.java.model.move.Move;


public class Hammerhead
        extends Shark {


    public Hammerhead(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Ensures:
     * moveList of HammerheadMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        setPieceMove(new HammerheadMove(startRow, startCol, 3, 1, 1));
        return getPieceMove().getMoveList();
    }

    @Override
    public List<Move> getAllPowerMoves(int startRow, int startCol) {
        return new HammerheadMove(startRow, startCol, 4, 2, 3).getMoveList();
    }

}
