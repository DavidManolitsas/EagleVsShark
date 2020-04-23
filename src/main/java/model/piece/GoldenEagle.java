package main.java.model.piece;

import main.java.model.move.GoldenEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class GoldenEagle
        extends Eagle {
    private List<int[]> sharkPosList;

    public GoldenEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Requires:
     * 1. startRow >= 0 && < BOARD.ROW (15)
     * 2. startCol >= 0 && < BOARD.COLUMN (10)
     * <p>
     * Ensures:
     * moveList of GoldenkMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new GoldenEagleMove(startRow, startCol, sharkPosList).getMoveList();
    }

    public void setSharkList(List<int[]> sharksPos) {
        this.sharkPosList = sharksPos;
    }
}
