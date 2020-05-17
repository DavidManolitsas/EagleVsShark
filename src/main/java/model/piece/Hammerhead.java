package main.java.model.piece;

import main.java.model.move.HammerheadMove;
import main.java.model.move.Move;

import java.util.List;


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
    public List<Move> getAllMoves(int startRow, int startCol, boolean isPowered) {
        return new HammerheadMove(startRow, startCol, isPowered).getMoveList();
    }
}
