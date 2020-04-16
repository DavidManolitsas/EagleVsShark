package main.java.model.piece;

import main.java.model.move.Move;

import java.util.List;


public abstract class Piece {
//    protected List<Move> allMovesList;

    // Initial position of piece
    private int[] startPos;

    /**
     * @param startRow >=0 && <= 14
     * @param startCol >= 0 && <= 9
     */
    private final int BOARD_COL_MAX = 9;
    private final int BOARD_ROW_MAX = 14;


    public Piece(int startRow, int startCol) {
//        coordinateValidation(startRow, startCol);
        startPos = new int[]{startRow, startCol};
//        allMovesList = new ArrayList<Move>();
    }

    /**
     * @param startRow >=0 && <= 14
     * @param startCol >= 0 && <= 9
     * @return array list of move objects
     */
    public abstract List<Move> getAllMoves(int startRow, int startCol);


    // am i supposed to have this for DbC? and get every method to call this?
    public void coordinateValidation(int startRow, int startCol) {
        if (startCol < 0 || startRow < 0 || startCol > BOARD_COL_MAX || startRow > BOARD_ROW_MAX) {
            throw new IllegalArgumentException("Piece coordinates are invalid");
        }
    }

    /**
     * @return Initial position of the piece
     */
    public int[] getStartPos() {
        return startPos;
    }
}
