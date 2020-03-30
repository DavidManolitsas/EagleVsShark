package main.java.controller;

import main.java.model.Board;
import main.java.model.piece.Piece;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewEventListener;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class BoardViewController
        implements BoardViewEventListener {

    private Board board;
    private BoardView boardView;

    public BoardViewController(Board model, BoardView view) {
        board = model;
        boardView = view;
    }

    @Override
    public void onSquareClicked(int row, int col) {
        Piece piece = board.getPiece(row, col);
        boardView.showValidMoves(row, col);
    }
}
