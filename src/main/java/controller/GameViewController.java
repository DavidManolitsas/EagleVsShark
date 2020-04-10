package main.java.controller;

import javafx.fxml.FXML;
import main.java.model.Board;
import main.java.model.Game;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewEventListener;
import main.java.view.GameInfoView;
import main.java.view.GameInfoView.GameInfoViewEventListener;

import java.util.List;

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
        game = Game.getInstance();
        game.setListener(gameInfoView);
        game.initGame();

        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
    }

    //region BoardView Event
    @Override
    public void onSquareClicked(int row, int col) {
        Piece piece = board.getPiece(row, col);
        if (piece == null) {
            return;
        }

        if (!game.pieceBelongsToPlayer(piece)) {
            return;
        }

        board.setChosenPiece(piece);
        List<Move> allMoves = piece.getMovesList(row, col);
        gameInfoView.showValidMoveList(allMoves);
        gameInfoView.showChosenPiece(piece);
    }
    //endregion

    //region GameInfoView Event
    @Override
    public void onMoveListItemClicked(Move move) {
        Move lastMove = board.getPreviewMove();
        if (lastMove != null) {
            boardView.removeMovePreview(lastMove);
        }
        boardView.showMovePreview(move);
        board.setPreviewMove(move);
    }

    @Override
    public void onMoveButtonClicked(Move move) {
        game.nextTurn();
        //update board and board view
        boardView.removeMovePreview(move);
        board.setPreviewMove(null);
        board.updatePiecePosition(move);
    }


    @Override
    public void timeRanOut() {
        gameInfoView.showTimeRanOutAlert();
    }

    @Override
    public void nextPlayerTurn() {
        game.nextTurn();
    }
    //endregion
}
