package main.java.model.piece;

import java.util.List;

import main.java.model.move.GoldenEagleMove;
import main.java.model.move.Move;

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
        setPieceMove(new GoldenEagleMove(startRow, startCol, 0, sharkPosList, false));
        return getPieceMove().getMoveList();
    }

    @Override
    public List<Move> getAllPowerMoves(int startRow, int startCol) {
        setPieceMove(new GoldenEagleMove(startRow, startCol, 0, sharkPosList, true));
        return getPieceMove().getMoveList();
    }

    public void setSharkList(List<int[]> sharksPos) {
        this.sharkPosList = sharksPos;
    }


}
