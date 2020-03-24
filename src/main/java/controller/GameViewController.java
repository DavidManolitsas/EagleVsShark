package main.java.controller;

import javafx.fxml.FXML;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewListener;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class GameViewController
        implements BoardViewListener {

    @FXML
    private BoardView boardView;

    @FXML
    public void initialize() {
        boardView.setBoardViewListener(this);
    }

    @Override
    public void onSquareClick(int row, int col) {
        boardView.changeSquareColor(row, col);
    }
}
