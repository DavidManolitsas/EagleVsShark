package main.java.model.piece;

import main.java.model.Player;
import main.java.model.board.Board;
import main.java.model.move.Move;

import java.util.List;


public class Piece {

    // Initial position of piece
    private int[] startPos;

    private PieceType pieceType;

    public Piece(PieceType pieceType, int startRow, int startCol) {
        this.pieceType = pieceType;
        startPos = new int[] {startRow, startCol};
    }

    public List<Move> availableMoves(int startRow, int startCol, boolean isPowered, Board board) {
        return pieceType.availableMoves(startRow, startCol, isPowered, board);
    }

    public boolean isBelongTo(Player player) {
        return pieceType.isBelongTo(player);
    }

    public String getImgPath() {
        return pieceType.getImgPath();
    }

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
