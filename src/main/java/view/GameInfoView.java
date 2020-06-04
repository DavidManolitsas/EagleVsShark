package main.java.view;

import java.util.List;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.java.model.Game.GameModelEventListener;
import main.java.model.Player;
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

        void onPowerMoveToggled(boolean isPowered);

        void onTimeRanOutAlertClosed();

        void onUndoButtonClicked(int undoMoves);

        void onStartUndoMove();
    }

    private GameInfoViewEventListener gameInfoViewEventListener;
    private static final double WIDTH = 250;
    private static final Font TITLE = Font.font("Impact", 24);
    private static final Font HEADING = Font.font("Helvetica", 16);
    private static final Font BODY = Font.font("Helvetica", 14);

    //Game Information components
    private VBox root;
    private VBox titleInfo;
    private VBox whoseTurn;
    private Text timeRemainingText;
    private Text playersTurnText;
    private Text sharkPowerMoveText;
    private Text eaglePowerMoveText;
    private Scoreboard scoreboard;
    private SelectMoveView selectMove;
    private Button undoBt;


    public GameInfoView() {
        initGameInfo();
    }

    private void initGameInfo() {
        root = new VBox();
        this.setPrefWidth(WIDTH);
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        this.setTop(root);
    }


    //region title and player names
    private void initTitleInfo(String sharkPlayerName, String eaglePlayerName) {
        titleInfo = new VBox();
        titleInfo.setSpacing(10);
        titleInfo.setPadding(new Insets(10, 0, 15, 0));
        titleInfo.setAlignment(Pos.CENTER);
        drawTitleAndPlayerNames(sharkPlayerName, eaglePlayerName);
        sharkPowerMoveText = new Text();
        sharkPowerMoveText.setFont(BODY);
        eaglePowerMoveText = new Text();
        eaglePowerMoveText.setFont(BODY);
        drawUndoMoveButton();
        root.getChildren().add(titleInfo);
    }

    public void drawTitleAndPlayerNames(String sharkPlayerName, String eaglePlayerName) {
        // title
        HBox eagleVsShark = new HBox();
        eagleVsShark.setSpacing(5);
        eagleVsShark.setAlignment(Pos.CENTER);
        Text eagleText = new Text("Eagle");
        eagleText.setFont(TITLE);
        eagleText.setFill(Color.ORANGE);
        Text vsText = new Text("vs.");
        vsText.setFont(TITLE);
        vsText.setFill(Color.BLACK);
        Text sharkText = new Text("Shark");
        sharkText.setFont(TITLE);
        sharkText.setFill(Color.valueOf("#3282b8"));
        eagleVsShark.getChildren().addAll(eagleText, vsText, sharkText);
        titleInfo.getChildren().add(eagleVsShark);


        // player names
        Text sharkPlayer = new Text(sharkPlayerName + " is the Shark Player");
        sharkPlayer.setFont(BODY);
        Text eaglePlayer = new Text(eaglePlayerName + " is the Eagle Player");
        eaglePlayer.setFont(BODY);
        titleInfo.getChildren().addAll(sharkPlayer, eaglePlayer);
    }

    public void drawPowerMoves(int sharkPowerMoves, int eaglePowerMoves) {
        sharkPowerMoveText.setText("Shark power moves: " + sharkPowerMoves);
        eaglePowerMoveText.setText("Eagle power moves: " + eaglePowerMoves);
        if (!titleInfo.getChildren().contains(sharkPowerMoveText)) {
            titleInfo.getChildren().addAll(sharkPowerMoveText, eaglePowerMoveText);
        }

    }

    public void drawUndoMoveButton() {
        undoBt = new Button("Undo Move");
        undoBt.setWrapText(true);
        undoBt.setFont(BODY);
        undoBt.setStyle("-fx-background-color: #1EB600; -fx-text-fill: WHITE");

        undoBt.setOnAction(event -> {
            getGameInfoViewEventListener().onStartUndoMove();
        });

        titleInfo.getChildren().add(undoBt);
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

        // Timer text
        timeRemainingText = new Text("Time remaining");
        timeRemainingText.setFont(HEADING);

        if (turnCount % 2 == 0) {
            playersTurnText.setFill(Color.ORANGE);
        } else {
            playersTurnText.setFill(Color.valueOf("#3282b8"));
        }

        whoseTurn.getChildren().addAll(playersTurnText, timeRemainingText);
        root.getChildren().add(whoseTurn);
    }

    private void drawPlayersTurn(int turnCount) {
        playersTurnText.setText(getPlayerTurnText(turnCount));
        if (turnCount % 2 == 0) {
            playersTurnText.setFill(Color.ORANGE);
        } else {
            playersTurnText.setFill(Color.valueOf("#3282b8"));
        }
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
        root.getChildren().add(scoreboard);
    }

    private void initSelectMoveView() {
        selectMove = new SelectMoveView(this);
        root.getChildren().add(selectMove);
    }

    public void showValidMoveList(List<Move> moves) {
        selectMove.showValidMoveList(moves);
    }

    public boolean isPowered() {
        return selectMove.isPowered();
    }

    public void setIsPowered(boolean isPowered) {
        selectMove.setIsPowered(isPowered);
    }

    private void turnOffPowered() {
        selectMove.turnOffPowered();
    }

    private void turnOnPowered() {
        selectMove.turnOnPowered();
    }

    public void showChosenPiece(Piece piece) {
        selectMove.showChosenPiece(piece);
    }

    private void checkRemainingPowerMoves(int turnCount, int sharkPowerMoves, int eaglePowerMoves) {
        boolean hasRemainingPowerMoves = (turnCount % 2 != 0 && sharkPowerMoves == 0) ||
                (turnCount % 2 == 0 && eaglePowerMoves == 0);

        if (hasRemainingPowerMoves) {
            turnOffPowered();
        } else {
            turnOnPowered();
        }
    }

    private void checkRemainingUndoMoves(int turnCount, int sharkUndoMoves, int eagleUndoMoves) {

        if (turnCount % 2 == 0 && eagleUndoMoves < 1) {
            disableUndoButton();
        } else if (turnCount % 2 != 0 && sharkUndoMoves < 1) {
            disableUndoButton();
        } else {
            enableUndoButton();
        }

    }

    public void disableUndoButton() {
        undoBt.setDisable(true);
    }

    public void enableUndoButton() {
        undoBt.setDisable(false);
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
            setIsPowered(false);
            gameInfoViewEventListener.onTimeRanOutAlertClosed();
        });

        a.show();
    }


    //region Game Event
    @Override
    public void gameInitialised(int turnCount, int totalTurns, int turnTime, double sharkScore, double eagleScore) {

        //initialise game information
        initTitleInfo(Player.SHARK.getName(), Player.EAGLE.getName());
        drawPowerMoves(Player.SHARK.getRemainingPowerMoves(), Player.EAGLE.getRemainingPowerMoves());
        initWhoseTurn(turnCount);
        initScoreboard(turnCount, totalTurns, sharkScore, eagleScore);
        initSelectMoveView();
    }

    @Override
    public void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore) {
        clearView();
        drawPowerMoves(Player.SHARK.getRemainingPowerMoves(), Player.EAGLE.getRemainingPowerMoves());
        drawPlayersTurn(turnCount);

        checkRemainingPowerMoves(turnCount,
                                 Player.SHARK.getRemainingPowerMoves(),
                                 Player.EAGLE.getRemainingPowerMoves());
        checkRemainingUndoMoves(turnCount, Player.SHARK.getUndoMoves(), Player.EAGLE.getUndoMoves());

        scoreboard.updateScoreboard(turnCount, sharkScore, eagleScore);
        selectMove.promptChoosePiece();
    }

    @Override
    public void timeRemainingChanged(int timeRemaining) {
        timeRemainingText.setText(timeRemaining + " seconds");
    }

    @Override
    public void timeRanOut() {
        showTimeRanOutAlert();
    }

    @Override
    public void onPieceSelected(Piece piece, List<Move> allPossibleMoves) {
        showValidMoveList(allPossibleMoves);
        showChosenPiece(piece);
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
