package main.java.model.piece;

import java.util.List;

import main.java.model.move.Move;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-19
 */
public abstract class Shark
        extends Piece {
    public Shark(int startRow, int startCol) {
        super(startRow, startCol);
    }

    public abstract List<Move> getAllPowerMoves(int startRow, int startCol);
}
