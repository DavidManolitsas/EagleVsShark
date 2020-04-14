package main.java.model.piece;

import main.java.model.move.Move;

import java.util.ArrayList;
import java.util.List;


public abstract class Piece {
    protected List<Move> allMovesList;

    public static final String DIRECTION_DOWN = "down";
    public static final String DIRECTION_UP = "up";
    public static final String DIRECTION_LEFT = "left";
    public static final String DIRECTION_RIGHT = "right";
    public static final String DIRECTION_DIAGONAL_UP_RIGHT = "diagonal up right";
    public static final String DIRECTION_DIAGONAL_UP_LEFT = "diagonal up left";

    // Initial position of piece
    private int[] startPos;

    public Piece(int startRow, int startCol) {
        startPos = new int[]{startRow, startCol};
        allMovesList = new ArrayList<Move>();
    }

    protected abstract List<Move> getAllMoves(int startRow, int startCol);

    public List<Move> getMovesList(int startRow, int startCol) {
        allMovesList.clear();
        getAllMoves(startRow, startCol);
        return allMovesList;
    }

    /**
     * @return Initial position of the piece
     */
    public int[] getStartPos() {
        return startPos;
    }
}
