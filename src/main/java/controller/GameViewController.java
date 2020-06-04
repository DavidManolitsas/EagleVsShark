package main.java.controller;

import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;
import javafx.application.Platform;
import javafx.fxml.FXML;
import main.java.model.Game;
import main.java.model.Game.GameBuilder;
import main.java.model.move.Move;
import main.java.util.SceneManager;
import main.java.view.GameInfoView;
import main.java.view.GameInfoView.GameInfoViewEventListener;
import main.java.view.MenuView;
import main.java.view.MenuView.MenuBarEventListener;
import main.java.view.UndoStage;
import main.java.view.board.BoardView.BoardViewEventListener;
import main.java.view.board.BoardViewI;
import main.java.view.board.HighlightDecorator;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 * <p>
 * Invariant:
 * 1. boardView !=null
 * 1. gameInfoView !=null
 * 1. game !=null
 */
@Invariant("boardView != null && gameInfoView != null && game != null")
public class GameViewController
        implements BoardViewEventListener,
                   GameInfoViewEventListener,
                   MenuBarEventListener {

    @FXML
    private BoardViewI boardView;

    @FXML
    private GameInfoView gameInfoView;

    @FXML
    private MenuView menuView;

    private UndoStage undoStage;

    private Game game;

    @FXML
    public void initialize() {
        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
        menuView.setListener(this);
        undoStage = new UndoStage(this);

        boardView = new HighlightDecorator(boardView);
    }

    public void initGameData(GameBuilder gameBuilder) {
        game = gameBuilder.addGameEventListener(gameInfoView)
                          .addGameEventListener(undoStage)
                          .setBoardEventListener(boardView)
                          .build();
        game.start();
    }

    //region BoardView Event

    /**
     * Requires:
     * 1. row >= 0 && col >= 0
     * 2. row < ROW && col < COL
     */
    @Override
    public void onSquareClicked(int row, int col) {
        game.onSquareClicked(row, col, gameInfoView.isPowered());
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
    @Requires("move != null")
    @Override
    public void onMoveButtonClicked(Move move) {
        if (move == null) {
            gameInfoView.showError("No move was selected");
            return;
        }

        game.onMoveButtonClicked(move);
    }

    @Override
    public void onPowerMoveToggled(boolean isPowered) {
        game.onPowerMoveToggled(isPowered);
        boardView.removeMovePreview();
    }

    @Override
    public void onTimeRanOutAlertClosed() {
        //the player ran out of time, change to next players turn
        game.nextTurn();
    }

    @Override
    public void onUndoButtonClicked(int undoMoves) {
        game.onUndoButtonClicked(undoMoves);
    }

    @Override
    public void onStartUndoMove() {
        undoStage.show();
    }
    //endregion

    // region MenuView Event
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
