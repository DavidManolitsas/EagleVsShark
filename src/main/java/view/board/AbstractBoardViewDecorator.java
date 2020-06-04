package main.java.view.board;

import javafx.scene.layout.StackPane;
import main.java.model.Player;
import main.java.model.Square;
import main.java.model.commands.AttackPieceInfo;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.view.board.BoardView.BoardViewEventListener;

import java.util.List;
import java.util.Set;

public class AbstractBoardViewDecorator
        implements BoardViewI {

    protected final BoardViewI BOARD_VIEW;

    public AbstractBoardViewDecorator(BoardViewI boardView) {
        this.BOARD_VIEW = boardView;
    }

    @Override
    public void onBoardInitialised(int totalRows, int totalCols, Set<Piece> keySet, List<Square> obstacleList) {
        BOARD_VIEW.onBoardInitialised(totalRows, totalCols, keySet, obstacleList);
    }

    @Override
    public void updatePiecePosition(int attackedRow, int attackedCol, int resetRow, int resetCol) {
        BOARD_VIEW.updatePiecePosition(attackedRow, attackedCol, resetRow, resetCol);
    }

    @Override
    public void onPieceSelected(int row, int col) {
        BOARD_VIEW.onPieceSelected(row, col);
    }

    @Override
    public void onPieceMoved(Move move, Player turnCount) {
        BOARD_VIEW.onPieceMoved(move, turnCount);
    }

    @Override
    public void onTimeRanOut() {
        BOARD_VIEW.onTimeRanOut();
    }

    @Override
    public void onUndoMove(Move move, List<Player> turnCount, AttackPieceInfo attackPieceInfo) {
        BOARD_VIEW.onUndoMove(move, turnCount, attackPieceInfo);
    }

    public StackPane getSquareAt(int row, int col) {
        return BOARD_VIEW.getSquareAt(row, col);
    }

    @Override
    public void setBoardViewEventListener(BoardViewEventListener boardViewEventListener) {
        BOARD_VIEW.setBoardViewEventListener(boardViewEventListener);
    }

    @Override
    public void showMovePreview(Move move) {
        BOARD_VIEW.showMovePreview(move);
    }

    @Override
    public void removeMovePreview() {
        BOARD_VIEW.removeMovePreview();

    }
}
