package main.java.view;

import java.util.List;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.java.model.Game.GameModelEventListener;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-31
 */
public class GameInfoView
        extends BorderPane
        implements GameModelEventListener {

    public interface GameInfoViewEventListener {
        void onMoveListItemClicked(Move move);

        void onMoveButtonClicked(Move move);

        void nextPlayerTurn();
    }

    private GameInfoViewEventListener gameInfoViewEventListener;
    private static final double WIDTH = 250;
    private static final Font TITLE = Font.font("Impact", 24);
    private static final Font HEADING = Font.font("Helvetica", 16);
    private static final Font BODY = Font.font("Helvetica", 14);

    //Game Information components
    private VBox rootGameInfo;
    private VBox titleInfo;

    private VBox whoseTurn;
    private Text timeRemainingText;
    private Text playersTurnText;

    private Scoreboard scoreboard;
    private SelectMoveView selectMove;


    public GameInfoView() {
        initGameInfo();
    }

    private void initGameInfo() {
        rootGameInfo = new VBox();
        this.setPrefWidth(WIDTH);
        rootGameInfo.setSpacing(10);
        rootGameInfo.setAlignment(Pos.CENTER);
        this.setTop(rootGameInfo);
    }


    //region title and player names
    private void initTitleInfo(String sharkPlayerName, String eaglePlayerName) {
        titleInfo = new VBox();
        titleInfo.setSpacing(10);
        titleInfo.setPadding(new Insets(15, 0, 0, 0));
        titleInfo.setAlignment(Pos.CENTER);
        drawTitle();
        drawPlayerNames(sharkPlayerName, eaglePlayerName);
        rootGameInfo.getChildren().add(titleInfo);
    }

    public void drawTitle() {
        Text title = new Text("Eagle vs. Shark");
        title.setFont(TITLE);
        title.setFill(Color.ORANGERED);
        titleInfo.getChildren().add(title);
    }

    public void drawPlayerNames(String sharkPlayerName, String eaglePlayerName) {
        Text sharkPlayer = new Text(sharkPlayerName + " is the Shark Player");
        sharkPlayer.setFont(BODY);
        Text eaglePlayer = new Text(eaglePlayerName + " is the Eagle Player\n");
        eaglePlayer.setFont(BODY);
        titleInfo.getChildren().addAll(sharkPlayer, eaglePlayer);
    }
    //endregion

    //region whose turn and timer
    public void initWhoseTurn(int turnCount) {
        whoseTurn = new VBox();
        whoseTurn.setSpacing(10);
        whoseTurn.setAlignment(Pos.CENTER);

        // Player turn text
        playersTurnText = new Text(getPlayerTurnText(turnCount));
        playersTurnText.setFont(HEADING);
        playersTurnText.setFill(Color.ORANGERED);
        whoseTurn.getChildren().add(playersTurnText);

        // Timer text
        timeRemainingText = new Text("Time remaining");
        timeRemainingText.setFont(HEADING);
        timeRemainingText.setFill(Color.ORANGERED);
        whoseTurn.getChildren().add(timeRemainingText);

        rootGameInfo.getChildren().add(whoseTurn);
    }

    private void drawPlayersTurn(int turnCount) {
        playersTurnText.setText(getPlayerTurnText(turnCount));
    }

    private String getPlayerTurnText(int turnCount) {
        if (turnCount % 2 == 0) {
            return "It's the Eagle's turn!";
        } else {
            return "It's the Shark's turn!";
        }
    }
    //endregion


    public void initScoreboard(int turnCount, int totalTurns, double sharkScore, double eagleScore) {
        scoreboard = new Scoreboard(turnCount, totalTurns, sharkScore, eagleScore);
        rootGameInfo.getChildren().add(scoreboard);
    }

    private void initSelectMoveView() {
        selectMove = new SelectMoveView(this);
        rootGameInfo.getChildren().add(selectMove);
    }

    public void showValidMoveList(List<Move> moves) {
        selectMove.showValidMoveList(moves);
    }

    public void showChosenPiece(Piece piece) {
        selectMove.showChosenPiece(piece);
    }

    private void updateGameInfo(int turnCount, double sharkScore, double eagleScore) {
        clearView();
        drawPlayersTurn(turnCount);
        scoreboard.updateScoreboard(turnCount, sharkScore, eagleScore);
        selectMove.promptChoosePiece();
    }


    private void clearView() {
        scoreboard.getChildren().clear();
        selectMove.clearMoveList();
    }

    public void showError(String message) {
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

    public void showTimeRanOutAlert() {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setHeaderText("You ran out of time!");
        a.setContentText("It's now the next players turn");
        ButtonType nextTurn = new ButtonType("Next Turn", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().setAll(nextTurn);

        a.setOnHidden(event -> {
            gameInfoViewEventListener.nextPlayerTurn();
        });

        a.show();
    }


    //region Game Event
    @Override
    public void gameInitialised(String sharkPlayerName, String eaglePlayerName,
                                int turnCount, int totalTurns, int turnTime, double sharkScore, double eagleScore) {

        //initialise game information
        initTitleInfo(sharkPlayerName, eaglePlayerName);
        initWhoseTurn(turnCount);
        initScoreboard(turnCount, totalTurns, sharkScore, eagleScore);
        initSelectMoveView();
    }

    @Override
    public void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore) {
        updateGameInfo(turnCount, sharkScore, eagleScore);
    }

    @Override
    public void timeRemainingChanged(int timeRemaining) {
        timeRemainingText.setText(timeRemaining + " seconds\n");
    }

    @Override
    public void timeRanOut() {
        showTimeRanOutAlert();
    }
    //endregion


    public GameInfoViewEventListener getGameInfoViewEventListener() {
        return Objects.requireNonNull(gameInfoViewEventListener);
    }

    public void setGameInfoViewEventListener(GameInfoViewEventListener gameInfoViewEventListener) {
        if (gameInfoViewEventListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.gameInfoViewEventListener = gameInfoViewEventListener;
    }

}
