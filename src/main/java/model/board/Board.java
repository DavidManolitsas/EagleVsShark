package main.java.model.board;

import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.model.player.Player;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-05-06
 */
public interface Board {
    void initBoard(int rows, int cols);

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
}
