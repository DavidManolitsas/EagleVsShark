package main.java.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import main.java.model.Board.BoardModelEventListener;

import java.util.Objects;

import static main.java.model.Board.COLUMN;
import static main.java.model.Board.ROW;

public class BoardView
        extends GridPane
        implements BoardModelEventListener {

    public interface BoardViewEventListener {
        void onPieceChosen(int row, int col);
    }

    private BoardViewEventListener boardViewEventListener;

    public BoardView() {
        super();
        drawBoard();
    }

    //region public BoardView methods
    public void changeSquareColor(int row, int col) {
        getSquareAt(row, col).setStyle("-fx-background-color: red;");
    }
    //endregion

    //region BoardModelEvent methods
    @Override
    public void onDataChanged() {

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
            getColumnConstraints().add(new ColumnConstraints(10,
                                                             Control.USE_COMPUTED_SIZE,
                                                             Double.POSITIVE_INFINITY,
                                                             Priority.ALWAYS,
                                                             HPos.CENTER,
                                                             true));
        }


        for (int i = 0; i < ROW; i++) {
            getRowConstraints().add(new RowConstraints(10,
                                                       Control.USE_COMPUTED_SIZE,
                                                       Double.POSITIVE_INFINITY,
                                                       Priority.ALWAYS,
                                                       VPos.CENTER,
                                                       true));
        }
    }

    private StackPane getSquare(int row, int col) {
        StackPane square = new StackPane();
        String color;
        if ((row + col) % 2 == 0) {
            color = "white";
        } else {
            color = "black";
        }
        square.setStyle("-fx-background-color: " + color + ";");
        square.setOnMouseClicked(event -> getBoardViewEventListener().onPieceChosen(row, col));
        return square;
    }

    private Node getSquareAt(int row, int col) {
        for (Node child : getChildren()) {
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                return child;
            }
        }
        return null;
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
