package main.java.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import main.java.model.Board;

import java.util.Objects;

import static main.java.Game.WINDOW_HEIGHT;
import static main.java.Game.WINDOW_WIDTH;

public class BoardView
        extends GridPane {

    public interface BoardViewListener {
        void onSquareClick(int row, int col);
    }

    private BoardViewListener boardViewListener;

    public BoardView() {
        super();
        drawBoard(Board.ROW);
    }

    // Just testing
    public void changeSquareColor(int row, int col) {
        getSquareAt(row, col).setStyle("-fx-background-color: red;");
    }

    private void drawBoard(int boardSize) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                StackPane square = getSquare(row, col);
                add(square, col, row);
            }
        }

        for (int i = 0; i < boardSize; i++) {
            getColumnConstraints().add(new ColumnConstraints(50,
                                                             WINDOW_WIDTH,
                                                             Double.POSITIVE_INFINITY,
                                                             Priority.ALWAYS,
                                                             HPos.CENTER,
                                                             true));
            getRowConstraints().add(new RowConstraints(50,
                                                       WINDOW_HEIGHT,
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
        square.setOnMouseClicked(event -> getBoardViewListener().onSquareClick(row, col));
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

    public BoardViewListener getBoardViewListener() {
        return Objects.requireNonNull(boardViewListener);
    }

    public void setBoardViewListener(BoardViewListener boardViewListener) {
        if (boardViewListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.boardViewListener = boardViewListener;
    }
}
