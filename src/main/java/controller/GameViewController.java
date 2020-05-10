package main.java.controller;

import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import main.java.model.Game;
import main.java.model.board.Board;
import main.java.model.board.BoardImpl;
import main.java.model.board.RockDecorator;
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
        board = new BoardImpl(boardView);
        game = new Game(gameInfoView);

        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
        menuView.setListener(this);
    }


    public void initGameData(String sharkPlayerName, String eaglePlayerName) {
        initGameData(sharkPlayerName, eaglePlayerName, 60, 40, 15, 10, 3, 3);
    }

    public void initGameData(String sharkPlayerName, String eaglePlayerName, int timeLimit, int turnCount, int rows,
                             int cols,
                             int sharks, int eagles) {


        board.initBoard(rows, cols, sharks, eagles);
        boardView.initBoardView(rows, cols, board.getTopRow(), board.getBottomRow());
        board = new RockDecorator(board);

        game.initialiseGame(sharkPlayerName, eaglePlayerName, timeLimit, turnCount, rows, cols);
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
