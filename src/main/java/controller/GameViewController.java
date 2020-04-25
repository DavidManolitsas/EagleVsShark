package main.java.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import main.java.model.Board;
import main.java.model.Game;
import main.java.model.move.Move;
import main.java.model.piece.GoldenEagle;
import main.java.model.piece.Piece;
import main.java.model.player.Player;
import main.java.util.SceneManager;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewEventListener;
import main.java.view.GameInfoView;
import main.java.view.GameInfoView.GameInfoViewEventListener;
import main.java.view.MenuView;
import main.java.view.MenuView.MenuBarEventListener;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 * <p>
 * Invariant:
 * 1. boardView !=null
 * 1. gameInfoView !=null
 * 1. game !=null
 * 1. board !=null
 */
public class GameViewController
        implements BoardViewEventListener,
                   GameInfoViewEventListener,
                   MenuBarEventListener {

    @FXML
    private BoardView boardView;

    @FXML
    private GameInfoView gameInfoView;

    @FXML
    private MenuView menuView;

    private Game game;

    private Board board;

    @FXML
    public void initialize() {
        board = new Board(boardView);
        game = new Game(gameInfoView);

        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
        menuView.setListener(this);
    }

    public void initGameData(String sharkPlayerName, String eaglePlayerName, int timeLimit) {
        game.initialiseGame(sharkPlayerName, eaglePlayerName, timeLimit);
        game.nextTurn();
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
    public void nextPlayerTurn() {
        //the player ran out of time, change to next players turn
        game.nextTurn();
    }
    //endregion

    // region BoardView Event
    @Override
    public void onNewGameClicked() {
        game.stopTimer();
        SceneManager.getInstance().showStartMenu();
    }

    @Override
    public void onHowToClicked() {
        game.pauseTimer();
    }

    @Override
    public void onHowToWindowClosed() {
        game.resumeTimer();
    }

    @Override
    public void onQuitClicked() {
        game.stopTimer();
        Platform.exit();
        System.exit(0);
    }
    // endregion
}
