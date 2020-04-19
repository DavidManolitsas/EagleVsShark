package main.java.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import main.java.ResPath;
import main.java.model.Board.BoardModelEventListener;
import main.java.model.move.Move;

import java.util.*;

import static main.java.model.Board.COLUMN;
import static main.java.model.Board.ROW;

public class BoardView
        extends GridPane
        implements BoardModelEventListener {

    public interface BoardViewEventListener {
        void onSquareClicked(int row, int col);
    }

    public static final String VIEW_ID_PIECE = "piece";
    public static final String VIEW_ID_PREVIEW = "preview";
    public static final String VIEW_ID_HIGHLIGHT = "highlight";

    public static final String COLOUR_EAGLE = "#ffebd9";
    public static final String COLOUR_SHARK = "#3282b8";
    public static final String COLOUR_NEUTRAL = "#f1f3f4";
    public static final String COLOUR_ROUTE_PREVIEW = "rgba(237, 124, 124, 0.37)";
    public static final String COLOUR_PAINT_AREA_PREVIEW = "rgba(193, 193, 193, 0.6)";

    public static final int SQUARE_SIZE = 40;
    public static final int PIECE_SIZE = 38;

    private int[] lastHighlightPos = null;
    private Move lastPreviewMove = null;

    private BoardViewEventListener boardViewEventListener;

    public BoardView() {
        super();
        drawBoard();

        drawPieces();
    }

    //region public BoardView methods
    public void highlightSquare(int row, int col) {
        if (lastHighlightPos != null) {
            removeHighlight();
        }

        StackPane square = getSquareAt(row, col);
        Node highlight = new StackPane();
        highlight.setId(VIEW_ID_HIGHLIGHT);
        highlight.setStyle("-fx-background-color: " + COLOUR_ROUTE_PREVIEW + ";");
        square.getChildren().add(highlight);

        lastHighlightPos = new int[] {row, col};

        removeMovePreview();
    }

    public void removeHighlight() {
        if (lastHighlightPos == null) {
            return;
        }

        StackPane square = getSquareAt(lastHighlightPos[0], lastHighlightPos[1]);
        if (square != null) {
            square.getChildren().removeIf(child -> child.getId().equals(VIEW_ID_HIGHLIGHT));
        }
        lastHighlightPos = null;
    }

    public void showMovePreview(Move move) {
        if (lastPreviewMove != null) {
            removeMovePreview();
        }

        for (int[] position : move.getPaintShape().getPaintInfo()) {
            paintSquare(position, COLOUR_PAINT_AREA_PREVIEW);
        }

        for (int[] position : move.getRoute()) {
            paintSquare(position, COLOUR_ROUTE_PREVIEW);
        }
        lastPreviewMove = move;
    }

    public void removeMovePreview() {
        if (lastPreviewMove == null) {
            return;
        }

        // Remove duplicated positions
        Set<int[]> paintInfo = new LinkedHashSet<>(lastPreviewMove.getPaintShape().getPaintInfo());
        paintInfo.addAll(lastPreviewMove.getRoute());
        List<int[]> allPositions = new ArrayList<>(paintInfo);

        for (int[] position : allPositions) {
            StackPane square = getSquareAt(position[0], position[1]);
            if (square != null) {
                square.getChildren().removeIf(child -> child.getId().equals(VIEW_ID_PREVIEW));
            }
        }
        lastPreviewMove = null;
    }

    public void updateTerritory(Move move, int turnCount) {
        String color;

        if (turnCount % 2 == 0) {
            color = COLOUR_EAGLE;
        } else {
            color = COLOUR_SHARK;
        }

        for (int[] position : move.getPaintShape().getPaintInfo()) {
            StackPane square = getSquareAt(position[0], position[1]);
            if (square != null) {
                square.setStyle("-fx-border-color: black; -fx-background-color: " + color + ";");
            }
        }
    }
    //endregion

    //region BoardModelEvent methods
    @Override
    public void onPiecePositionUpdated(Move move) {
        int[] startPos = move.getRoute().get(0);
        int[] destinationPos = move.getFinalPosition();

        StackPane start = getSquareAt(startPos[0], startPos[1]);
        Node piece = start.getChildren().filtered(child -> child.getId().equals(VIEW_ID_PIECE)).get(0);
        start.getChildren().remove(piece);

        StackPane destination = getSquareAt(destinationPos[0], destinationPos[1]);
        destination.getChildren().add(piece);
    }
    //endregion

    //region private methods
    private void drawBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                StackPane square = getSquare(row, col);
                add(square, col, row);
            }
        }

        for (int i = 0; i < COLUMN; i++) {
            getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE,
                                                             Control.USE_COMPUTED_SIZE,
                                                             Double.POSITIVE_INFINITY,
                                                             Priority.ALWAYS,
                                                             HPos.CENTER,
                                                             true));
        }


        for (int i = 0; i < ROW; i++) {
            getRowConstraints().add(new RowConstraints(SQUARE_SIZE,
                                                       Control.USE_COMPUTED_SIZE,
                                                       SQUARE_SIZE,
                                                       Priority.ALWAYS,
                                                       VPos.CENTER,
                                                       true));
        }
    }

    private void drawPieces() {
        int topRow = 0;
        int bottomRow = ROW - 1;
        int[] pieceRows = {topRow, bottomRow};
        int[] pieceCols = {4, 5, 6};

        String[] images = {
                ResPath.PIECE_BALD_EAGLE, ResPath.PIECE_GOLDEN_EAGLE, ResPath.PIECE_HARPY_EAGLE,
                ResPath.PIECE_GOBLIN_SHARK, ResPath.PIECE_HAMMERHEAD, ResPath.PIECE_SAW_SHARK
        };

        int index = 0;
        for (int row : pieceRows) {
            for (int col : pieceCols) {
                addPiece(row, col, images[index++]);
            }
        }
    }

    private StackPane getSquare(int row, int col) {
        StackPane square = new StackPane();
        square.setStyle("-fx-border-color: black; -fx-background-color: " + COLOUR_NEUTRAL + ";");
        square.setOnMouseClicked(event -> getBoardViewEventListener().onSquareClicked(row, col));
        return square;
    }

    private StackPane getSquareAt(int row, int col) {
        for (Node child : getChildren()) {
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                return (StackPane) child;
            }
        }
        return null;
    }

    private void addPiece(int row, int col, String pieceImgPath) {
        StackPane square = getSquareAt(row, col);

        if (square == null) {
            return;
        }

        Image image = new Image(pieceImgPath, PIECE_SIZE, PIECE_SIZE, true, false);
        ImageView imageView = new ImageView(image);
        imageView.setId(VIEW_ID_PIECE);
        square.getChildren().add(imageView);
    }

    private void paintSquare(int[] position, String color) {
        StackPane square = getSquareAt(position[0], position[1]);
        if (square != null) {
            Node route = new StackPane();
            route.setStyle("-fx-background-color: " + color + ";");
            route.setId(VIEW_ID_PREVIEW);
            square.getChildren().add(route);
        }
    }
    //endregion

    public BoardViewEventListener getBoardViewEventListener() {
        return Objects.requireNonNull(boardViewEventListener);
    }

    public void setBoardViewEventListener(BoardViewEventListener boardViewEventListener) {
        if (boardViewEventListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.boardViewEventListener = boardViewEventListener;
    }
}
