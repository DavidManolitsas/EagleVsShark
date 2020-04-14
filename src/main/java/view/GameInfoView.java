package main.java.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
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

        void timeRanOut();

        void nextPlayerTurn();
    }

    private GameInfoViewEventListener gameInfoViewEventListener;

    private static final Font TITLE = Font.font("Impact", 28);
    private static final Font HEADING = Font.font("Helvetica", 18);
    private static final Font BODY = Font.font("Helvetica", 16);
    private ListView<Move> moveList;
    private DecimalFormat decimalFormat = new DecimalFormat("#%");
    private int totalTurns;
    private Integer startTime;
    private Integer timeRemaining;
    //Game Information components
    private VBox rootGameInfo = new VBox();
    private VBox titleInfo = new VBox();
    private VBox whoseTurn = new VBox();
    private VBox scoreInfo = new VBox();
    private VBox chosenPiece = new VBox();
    private VBox movement;
    //timer
    Timeline time;

    public GameInfoView() {
        initGameInfo();
    }

    private void initGameInfo() {
        rootGameInfo.setSpacing(10);
        rootGameInfo.setAlignment(Pos.CENTER);
        this.setTop(rootGameInfo);
    }


    //start region move list
    private void initMovement() {
        movement = new VBox();
        moveList = new ListView<>();
        moveList.setFixedCellSize(50);
        moveList.setPrefHeight(470);
        movement.getChildren().add(moveList);
        this.setBottom(movement);
    }

    private void drawMoveButton() {
        if (moveList != null) {
            Button moveBt = new Button("Move Piece");
            moveBt.setWrapText(true);
            moveBt.setFont(HEADING);
            moveBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
            moveBt.setPrefWidth(250);

            moveBt.setOnAction(event -> {
                deleteTimer();
                gameInfoViewEventListener.onMoveButtonClicked(getSelectedMove());
            });

            movement.getChildren().add(moveBt);
        }

    }

    public void showValidMoveList(List<Move> moves) {
        movement.getChildren().clear();
        movement.getChildren().add(moveList);
        // update the move list
        ObservableList<Move> moveListObservable = FXCollections.observableArrayList(moves);
        moveList.getItems().removeAll();
        moveList.setItems(moveListObservable);
        moveList.setStyle("-fx-selection-bar: rgba(255,69,0,0.33)");

        // assign name to each Move object
        moveList.setCellFactory(e -> new ListCell<Move>() {

            protected void updateItem(Move item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Move " + (moves.indexOf(item) + 1));
                    setTextAlignment(TextAlignment.CENTER);
                    setFont(BODY);

                }
            }

        });

        moveList.setOnMouseClicked(event -> {
            Move move = getSelectedMove();
            if (move != null) {
                getGameInfoViewEventListener().onMoveListItemClicked(getSelectedMove());
            }
        });

        drawMoveButton();

    }

    private Move getSelectedMove() throws NullPointerException {
        return moveList.getSelectionModel().getSelectedItem();
    }
    //end region


    //start region title and player names
    private void initTitleInfo(String sharkPlayerName, String eaglePlayerName) {
        titleInfo.setSpacing(10);
        titleInfo.setPadding(new Insets(30, 0, 0, 0));
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
    //end region


    //start region whose turn and timer
    public void initWhoseTurn(int turnCount) {
        whoseTurn.setSpacing(10);
        whoseTurn.setAlignment(Pos.CENTER);
        drawPlayersTurn(turnCount);
        drawTimer();
        rootGameInfo.getChildren().add(whoseTurn);
    }

    private void drawPlayersTurn(int turnCount) {
        Text playersTurn = new Text(getPlayerTurnText(turnCount));
        playersTurn.setFont(HEADING);
        playersTurn.setFill(Color.ORANGERED);
        whoseTurn.getChildren().add(playersTurn);
    }

    private String getPlayerTurnText(int turnCount) {
        if (turnCount % 2 == 0) {
            return "It's the Eagle's turn!";
        } else {
            return "It's the Shark's turn!";
        }
    }

    private void drawTimer() {
        Text timeRemainingText = new Text(timeRemaining.toString() + " seconds\n");
        timeRemainingText.setFont(HEADING);
        timeRemainingText.setFill(Color.ORANGERED);
        whoseTurn.getChildren().add(timeRemainingText);

        time = new Timeline();
        time.setCycleCount(startTime);

        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                //decrement time
                timeRemaining--;
                //draw time remaining
                timeRemainingText.setText(timeRemaining.toString() + " seconds\n");
                //check if timer has finished
                if (timeRemaining <= 0) {
                    time.stop();
                    gameInfoViewEventListener.timeRanOut();
                }
            }

        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    public void resetTimer() {
        timeRemaining = startTime;
    }
    //end region


    // start region score info
    public void initScoreInfo(int turnCount, double sharkScore, double eagleScore) {
        scoreInfo.setSpacing(10);
        scoreInfo.setAlignment(Pos.CENTER);
        drawTurnCount(turnCount);
        drawScores(sharkScore, eagleScore);

        rootGameInfo.getChildren().add(scoreInfo);
    }

    public void drawTurnCount(int turnCount) {
        Text turnCountText = new Text("Turn No. " + turnCount + "/" + totalTurns);
        turnCountText.setFont(BODY);
        scoreInfo.getChildren().add(turnCountText);
    }

    private void drawScores(double sharkScore, double eagleScore) {
        Text sharkScoreText = new Text("Shark Score: " + decimalFormat.format(sharkScore));
        sharkScoreText.setFont(BODY);
        Text eagleScoreText = new Text("Eagle Score: " + decimalFormat.format(eagleScore));
        eagleScoreText.setFont(BODY);
        scoreInfo.getChildren().addAll(sharkScoreText, eagleScoreText);
    }
    //end region

    //start region chosen piece
    public void initChosenPiece() {
        chosenPiece.setSpacing(10);
        chosenPiece.setPadding(new Insets(0, 0, 15, 0));
        chosenPiece.setAlignment(Pos.CENTER);
        drawChoosePiece();
        rootGameInfo.getChildren().add(chosenPiece);
    }

    public void drawChoosePiece() {
        Text choosePieceText = new Text("\nChoose a piece to move");
        choosePieceText.setFont(HEADING);
        choosePieceText.setFill(Color.ORANGERED);
        chosenPiece.getChildren().add(choosePieceText);
    }

    public void showChosenPiece(Piece piece) {
        chosenPiece.getChildren().clear();
        Text pieceName = new Text("\n" + getPieceName(piece) + " selected");
        pieceName.setFont(HEADING);
        pieceName.setFill(Color.ORANGERED);
        chosenPiece.getChildren().add(pieceName);
    }

    public String getPieceName(Piece piece) {
        String name = piece.getClass().getSimpleName();
        String pieceName = name.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
        return pieceName;
    }
    //end region


    private void updateGameInfo(int turnCount, double sharkScore, double eagleScore) {
        clearView();
        drawPlayersTurn(turnCount);
        resetTimer();
        drawTimer();
        drawTurnCount(turnCount);
        drawScores(sharkScore, eagleScore);
        drawChoosePiece();
    }

    private void clearView() {
        whoseTurn.getChildren().clear();
        scoreInfo.getChildren().clear();
        chosenPiece.getChildren().clear();
        movement.getChildren().clear();
        movement.getChildren().add(moveList);
        moveList.getItems().clear();
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

        //initialise number of turns and time per turn
        this.totalTurns = totalTurns;
        this.startTime = turnTime;
        this.timeRemaining = turnTime;
        //initialise game information
        initTitleInfo(sharkPlayerName, eaglePlayerName);
        initWhoseTurn(turnCount);
        initScoreInfo(turnCount, sharkScore, eagleScore);
        initChosenPiece();
        initMovement();
    }

    @Override
    public void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore) {
        updateGameInfo(turnCount, sharkScore, eagleScore);
    }

    @Override
    public void stopTimer() {
        time.stop();
    }

    @Override
    public void startTimer() {
        time.play();
    }

    @Override
    public void deleteTimer() {
        time.stop();
        time = null;
    }
    //end region

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
