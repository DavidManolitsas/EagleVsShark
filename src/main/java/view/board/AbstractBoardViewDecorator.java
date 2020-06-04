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

    protected final BoardViewI boardView;

    public AbstractBoardViewDecorator(BoardViewI boardView) {
        this.boardView = boardView;
    }

    @Override
    public void onBoardInitialised(int totalRows, int totalCols, Set<Piece> keySet, List<Square> obstacleList) {
        boardView.onBoardInitialised(totalRows, totalCols, keySet, obstacleList);
    }

    @Override
    public void updatePiecePosition(int attackedRow, int attackedCol, int resetRow, int resetCol) {
        boardView.updatePiecePosition(attackedRow, attackedCol, resetRow, resetCol);
    }

    @Override
    public void onPieceSelected(int row, int col) {
        boardView.onPieceSelected(row, col);
    }

    @Override
    public void onPieceMoved(Move move, Player turnCount) {
        boardView.onPieceMoved(move, turnCount);
    }

    @Override
    public void onTimeRanOut() {
        boardView.onTimeRanOut();
    }

    @Override
    public void onUndoMove(Move move, List<Player> turnCount, AttackPieceInfo attackPieceInfo) {
        boardView.onUndoMove(move, turnCount, attackPieceInfo);
    }

    public StackPane getSquareAt(int row, int col) {
        return boardView.getSquareAt(row, col);
    }

    @Override
    public void setBoardViewEventListener(BoardViewEventListener boardViewEventListener) {
        boardView.setBoardViewEventListener(boardViewEventListener);
    }

    @Override
    public void showMovePreview(Move move) {
        boardView.showMovePreview(move);
    }

    @Override
    public void removeMovePreview() {
        boardView.removeMovePreview();

    }
}
