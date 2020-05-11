package main.java.model.piece;

import java.util.List;

import main.java.model.move.Move;


public abstract class Piece {

    // Initial position of piece
    private int[] startPos;

    public Piece(int startRow, int startCol) {
        startPos = new int[]{startRow, startCol};
    }

    /**
     * Requires:
     * 1. startRow >= 0 && < BOARD.ROW (15)
     * 2. startCol >= 0 && < BOARD.COLUMN (10)
     *
     * Ensures:
     * 1. moveList !null
     */
    public abstract List<Move> getAllMoves(int startRow, int startCol);

    /**
     * @return Initial position of the piece
     */
    public int[] getStartPos() {
        return startPos;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getStartPos()[0] + " " + getStartPos()[1];
    }
}
