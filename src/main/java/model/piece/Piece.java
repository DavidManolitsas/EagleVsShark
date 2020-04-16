package main.java.model.piece;

import main.java.model.move.Move;

import java.util.ArrayList;
import java.util.List;


public abstract class Piece {
    protected List<Move> allMovesList;

    // Initial position of piece
    private int[] startPos;

    public Piece(int startRow, int startCol) {
        startPos = new int[]{startRow, startCol};
        allMovesList = new ArrayList<Move>();
    }

    public abstract List<Move> getAllMoves(int startRow, int startCol);

    /**
     * @return Initial position of the piece
     */
    public int[] getStartPos() {
        return startPos;
    }
}
