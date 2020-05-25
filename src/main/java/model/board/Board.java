package main.java.model.board;

import main.java.model.Player;
import main.java.model.Square;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

import java.util.List;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2020-05-06
 */
public interface Board {
    int getSharkSquareCount();

    int getEagleSquareCount();

    boolean retrieveSteps(int steps, Player player);

    Piece getPiece(int row, int col);

    Piece getChosenPiece();

    List<Move> validatePossibleMoves(List<Move> moves);

    Square getSquareAt(int row, int col);

    void onPieceSelected(Piece piece, int row, int col);

    void onMoveButtonClicked(Move move, Player currentPlayer);

    int[] getPiecePosition(Piece chosenPiece);

    void timeRantOut();

    Map<Piece, Square> getPieceSquareMap();

    Square[][] getSquares();

    int getTotalRows();

    int getTotalCols();

    List<int[]> getSharksPositions();
}
