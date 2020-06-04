package main.java.view.board;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import main.java.model.Player;
import main.java.model.Square;
import main.java.model.commands.AttackPieceInfo;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

import java.util.List;
import java.util.Set;

public class HighlightDecorator
        extends AbstractBoardViewDecorator {

    public static final String VIEW_ID_HIGHLIGHT = "highlight";
    public static final String COLOUR_HIGHLIGHT = "rgba(237, 124, 124, 0.37)";

    private int[] lastHighlightPos = null;

    public HighlightDecorator(BoardViewI boardView) {
        super(boardView);
    }

    @Override
    public void onBoardInitialised(int totalRows, int totalCols, Set<Piece> keySet, List<Square> obstacleList) {
        super.onBoardInitialised(totalRows, totalCols, keySet, obstacleList);
        lastHighlightPos = null;
    }

    @Override
    public void onPieceSelected(int row, int col) {
        super.onPieceSelected(row, col);
        highlightSquare(row, col);
    }

    @Override
    public void onPieceMoved(Move move, Player turnCount) {
        super.onPieceMoved(move, turnCount);
        removeHighlight();
    }

    @Override
    public void onTimeRanOut() {
        super.onTimeRanOut();
        removeHighlight();
    }

    @Override
    public void onUndoMove(Move move, List<Player> turnCount, AttackPieceInfo attackPieceInfo) {
        super.onUndoMove(move, turnCount, attackPieceInfo);
        removeHighlight();
    }

    private void highlightSquare(int row, int col) {
        if (lastHighlightPos != null) {
            removeHighlight();
        }

        StackPane square = super.getSquareAt(row, col);
        Node highlight = new StackPane();
        highlight.setId(VIEW_ID_HIGHLIGHT);
        highlight.setStyle("-fx-background-color: " + COLOUR_HIGHLIGHT + ";");
        square.getChildren().add(highlight);

        lastHighlightPos = new int[] {row, col};
    }

    private void removeHighlight() {
        if (lastHighlightPos == null) {
            return;
        }

        StackPane square = super.getSquareAt(lastHighlightPos[0], lastHighlightPos[1]);
        if (square != null) {
            square.getChildren().removeIf(child -> child.getId().equals(VIEW_ID_HIGHLIGHT));
        }
        lastHighlightPos = null;
    }
}
