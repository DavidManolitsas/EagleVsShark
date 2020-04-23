package main.java.controller;

import javafx.fxml.FXML;
import main.java.model.Board;
import main.java.model.Game;
import main.java.model.Player;
import main.java.model.move.Move;
import main.java.model.piece.GoldenEagle;
import main.java.model.piece.Piece;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewEventListener;
import main.java.view.GameInfoView;
import main.java.view.GameInfoView.GameInfoViewEventListener;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 *
 * Invariant:
 * 1. boardView !=null
 * 1. gameInfoView !=null
 * 1. game !=null
 * 1. board !=null
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
        game.initGameListener();

        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
    }

    //region BoardView Event

    /**
     * Requires:
     * 1. row >= 0 && col >= 0
     * 2. row < ROW && col < COL
     */
    @Override
    public void onSquareClicked(int row, int col) {
        Piece piece = board.getPiece(row, col);
        Piece prevChosenPiece = board.getChosenPiece();
        if (piece == null || piece == prevChosenPiece) {
            return;
        }

        if (!game.pieceBelongsToPlayer(piece)) {
            return;
        }

        if (piece instanceof GoldenEagle) {
            List<int[]> sharksPositions = board.getSharksPositions();
            ((GoldenEagle) piece).setSharkList(sharksPositions);
        }

        boardView.removeMovePreview();
        boardView.highlightSquare(row, col);

        board.setChosenPiece(piece);
        List<Move> allPossibleMoves = piece.getAllMoves(row, col);
        allPossibleMoves = board.validatePossibleMoves(allPossibleMoves);

        gameInfoView.showValidMoveList(allPossibleMoves);
        gameInfoView.showChosenPiece(piece);
    }
    //endregion

    //region GameInfoView Event
    @Override
    public void onMoveListItemClicked(Move move) {
        if (move == null) {
            return;
        }

        boardView.showMovePreview(move);
    }

    /**
     * Requires:
     * 1. move != null
     */
    @Override
    public void onMoveButtonClicked(Move move) {
        if (move == null) {
            gameInfoView.showError("No move was selected");
            return;
        }

        // Remove preview
        boardView.removeMovePreview();
        boardView.removeHighlight();

        // Update board
        Piece piece = board.getChosenPiece();
        Player currentPlayer = game.getCurrentPlayer();
        board.updatePiecePosition(move, piece);
        board.updateTerritory(move, currentPlayer);
        boardView.updateTerritory(move, game.getTurnCount());

        //the player moved their piece, change to next players turn
        game.updateSquareCount(board.getSharkSquareCount(), board.getEagleSquareCount());
        game.nextTurn();
    }

    @Override
    public void timeRanOut() {
        gameInfoView.showTimeRanOutAlert();
    }

    @Override
    public void nextPlayerTurn() {
        //the player ran out of time, change to next players turn
        game.nextTurn();
    }
    //endregion
}
