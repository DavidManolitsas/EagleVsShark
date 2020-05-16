package main.java.model.board;

import main.java.model.Square;
import main.java.model.board.BoardImpl.BoardModelEventListener;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.model.player.Player;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-05-06
 */
public interface Board {
    int getSharkSquareCount();

    int getEagleSquareCount();

    Piece getPiece(int row, int col);

    Piece getChosenPiece();

    void initBoard();

    List<Move> validatePossibleMoves(List<Move> moves);

    void setListener(BoardModelEventListener eventListener);

    Square getSquareAt(int row, int col);

    void onPieceSelected(Piece piece, int row, int col);

    void onMoveButtonClicked(Move move, Player currentPlayer, int turnCount);

    int[] getPiecePosition(Piece chosenPiece);
}
