package main.java.controller;

import java.util.List;

import javafx.fxml.FXML;
import main.java.model.Board;
import main.java.model.Game;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewEventListener;
import main.java.view.GameInfoView;
import main.java.view.GameInfoView.GameInfoViewEventListener;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class GameViewController
        implements BoardViewEventListener,
                   GameInfoViewEventListener {

    @FXML
    private BoardView boardView;

    @FXML
    private GameInfoView gameInfoView;

    private Game game;

    private Board board;

    @FXML
    public void initialize() {
        board = new Board(boardView);
        game = new Game();
        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
    }

    //region BoardView Event
    @Override
    public void onSquareClicked(int row, int col) {
        Piece piece = board.getPiece(row, col);

        if (piece != null) {
            board.setChosenPiece(piece);
            List<Move> allMoves = piece.getAllMoves(row, col);
            gameInfoView.showValidMoveList(allMoves);
        }
    }
    //endregion

    //region GameInfoView Event
    @Override
    public void onMoveListItemClicked(Move move) {
        boardView.showMoveRoute(move);
    }

    @Override
    public void onMoveButtonClicked(Move move) {
        //TODO: implement, I have passed the move from the gameInfoView
    }
    //endregion
}
