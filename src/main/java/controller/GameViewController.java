package main.java.controller;

import javafx.fxml.FXML;
import main.java.model.Board;
import main.java.view.BoardView;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class GameViewController {

    @FXML
    private BoardView boardView;

    private Board board;

    @FXML
    public void initialize() {
        board = new Board(boardView);
        boardView.setBoardViewEventListener(new BoardViewController(board, boardView));
    }
}
