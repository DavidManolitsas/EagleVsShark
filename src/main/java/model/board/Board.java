package main.java.model.board;

import main.java.model.Player;
import main.java.model.Square;
import main.java.model.commands.AttackPieceInfo;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

import java.util.List;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2020-05-06
 */
public interface Board {

    Piece getPiece(int row, int col);

    Piece getChosenPiece();

    Square getSquareAt(int row, int col);

    void onPieceSelected(Piece piece, int row, int col);

    AttackPieceInfo onMoveButtonClicked(Move move, Player currentPlayer);

    void undoMove(Move move, Piece piece, Player currentPlayer, List<Player> occupiedPlayerHistory, AttackPieceInfo attackPieceInfo);

    int[] getPiecePosition(Piece chosenPiece);

    void timeRantOut();

    Map<Piece, Square> getPieceSquareMap();

    Square[][] getSquares();

    int getTotalRows();

    int getTotalCols();

    List<int[]> getSharksPositions();
}
