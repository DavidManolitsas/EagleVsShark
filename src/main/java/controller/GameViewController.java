package main.java.controller;

import javafx.fxml.FXML;
import main.java.model.Board;
import main.java.model.piece.Piece;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewEventListener;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class GameViewController
        implements BoardViewEventListener {

    @FXML
    private BoardView boardView;

    private Board board;

    @FXML
    public void initialize() {
        board = new Board(boardView);
    }

    @Override
    public void onSquareClicked(int row, int col) {
        Piece piece = board.getPiece(row, col);
//        if (piece != null) {
//            allValidMoves = piece.getAllMoves(row, col);
//            boardView.showValidMoves(allValidMoves);
//        }
    }
}
