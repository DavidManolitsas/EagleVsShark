package main.java.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import main.java.model.Board;

import static main.java.Game.WINDOW_HEIGHT;
import static main.java.Game.WINDOW_WIDTH;

public class BoardView
        extends GridPane {

    public BoardView() {
        super();
        drawBoard(Board.ROW);
    }

    private void drawBoard(int boardSize) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                StackPane square = new StackPane();
                String color;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                square.setStyle("-fx-background-color: " + color + ";");
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

    private Node getSquare(int row, int col) {
        for (Node child : getChildren()) {
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                return child;
            }
        }
        return null;
    }
}
