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

import java.util.Objects;

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

    public static final String COLOUR_EAGLE = "#ffebd9";
    public static final String COLOUR_SHARK = "#3282b8";
    public static final String COLOUR_NEUTRAL = "#f1f3f4";

    private BoardViewEventListener boardViewEventListener;

    public BoardView() {
        super();
        drawBoard();

        drawPieces();
    }

    //region public BoardView methods
    public void showMovePreview(Move move) {
        for (int[] position : move.getRoute()) {
            StackPane square = getSquareAt(position[0], position[1]);
            if (square != null) {
                Node route = new StackPane();
                route.setStyle("-fx-background-color: rgba(51,153,204,0.6);");
                route.setId(VIEW_ID_PREVIEW);
                square.getChildren().add(route);
            }
        }
    }

    public void removeMovePreview(Move move) {
        for (int[] position : move.getRoute()) {
            StackPane square = getSquareAt(position[0], position[1]);
            if (square != null) {
                square.getChildren().removeIf(child -> child.getId().equals(VIEW_ID_PREVIEW));
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
            getColumnConstraints().add(new ColumnConstraints(50,
                                                             Control.USE_COMPUTED_SIZE,
                                                             Double.POSITIVE_INFINITY,
                                                             Priority.ALWAYS,
                                                             HPos.CENTER,
                                                             true));
        }


        for (int i = 0; i < ROW; i++) {
            getRowConstraints().add(new RowConstraints(50,
                                                       Control.USE_COMPUTED_SIZE,
                                                       Double.POSITIVE_INFINITY,
                                                       Priority.ALWAYS,
                                                       VPos.CENTER,
                                                       true));
        }
    }

    private void drawPieces() {
        // TODO: Refactor test code
        int[] row = {0, 14};
        int[] col = {4, 5, 6};
        String[] images = {
                ResPath.PIECE_BALD_EAGLE, ResPath.PIECE_GOLDEN_EAGLE, ResPath.PIECE_HARPY_EAGLE,
                ResPath.PIECE_GOBLIN_SHARK, ResPath.PIECE_HAMMER_HEARD, ResPath.PIECE_SAW_SHARK
        };

        int index = 0;
        for (int i : row) {
            for (int j : col) {
                addPiece(i, j, images[index++]);
            }
        }
    }

    private StackPane getSquare(int row, int col) {
        StackPane square = new StackPane();
        String color;

        if (row < 7) {
            color = COLOUR_SHARK;
        } else {
            color = COLOUR_EAGLE;
        }
        square.setStyle("-fx-border-color: black; -fx-background-color: " + color + ";");
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

        Image image = new Image(pieceImgPath, 50, 50, true, false);
        ImageView imageView = new ImageView(image);
        imageView.setId(VIEW_ID_PIECE);
        square.getChildren().add(imageView);
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
