package main.java.model.board;

import java.util.List;

import main.java.model.Square;
import main.java.model.board.BoardImpl.BoardModelEventListener;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.model.player.Player;

/**
 * @author WeiYi Yu
 * @date 2020-05-06
 */
public interface Board {
    void initBoard(int rows, int cols, int sharks, int eagles);

    int getSharkSquareCount();

    int getEagleSquareCount();

    Piece getPiece(int row, int col);

    void setChosenPiece(Piece chosenPiece);

    Piece getChosenPiece();

    void updatePiecePosition(Move move, Piece piece);

    void updateTerritory(Move move, Player player);

    List<int[]> getSharksPositions();

    List<Move> validatePossibleMoves(List<Move> moves);

    boolean isSquareValid(int[] position);

    int getTotalRows();

    int getTotalCols();

    BoardModelEventListener getEventListener();

    Square[] getTopRow();

    Square[] getBottomRow();

    Square getSquareAt(int row, int col);

    Square getSelectedSquare();

    void setSelectedSquare(Square selectedSquare);

}
