package main.java.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    }

    private GameInfoViewEventListener gameInfoViewEventListener;

    private static final Font TITLE = Font.font("Impact", 28);
    private static final Font HEADING = Font.font("Helvetica", 18);
    private static final Font BODY = Font.font("Helvetica", 14);
    private ListView<Move> moveList;
    private DecimalFormat decimalFormat = new DecimalFormat("#%");
    private int totalTurns;
    //Game Information components
    private VBox rootGameInfo = new VBox();
    private VBox titleInfo = new VBox();
    private VBox whoseTurn = new VBox();
    private VBox scoreInfo = new VBox();
    private VBox chosenPiece = new VBox();


    public GameInfoView() {
        initGameInfo();
    }

    private void initGameInfo() {
        rootGameInfo.setPadding(new Insets(40, 20, 40, 20));
        rootGameInfo.setSpacing(10);
        rootGameInfo.setAlignment(Pos.CENTER);
        this.setTop(rootGameInfo);
    }


    //start region move list
    private void drawMoveList() {
        if (moveList != null) {
            Button moveBt = new Button("Move Piece");
            moveBt.setWrapText(true);
            moveBt.setFont(HEADING);
            moveBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
            moveBt.setPrefWidth(250);

            moveBt.setOnAction(event -> {
                getGameInfoViewEventListener().onMoveButtonClicked(getSelectedMove());
            });

            this.setBottom(moveBt);
        }

    }

    public void showValidMoveList(List<Move> moves) {
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

        //redraw game info
        drawMoveList();
    }

    private Move getSelectedMove() throws NullPointerException {
        return moveList.getSelectionModel().getSelectedItem();
    }

    private void initMoveList() {
        moveList = new ListView<>();
        moveList.setFixedCellSize(50);
        this.setCenter(moveList);
    }
    //end region


    //start region title and player names
    private void initTitleInfo(String sharkPlayerName, String eaglePlayerName) {
        titleInfo.setSpacing(10);
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


    //start region whose turn
    public void initWhoseTurn(int turnCount) {
        whoseTurn.setSpacing(10);
        whoseTurn.setAlignment(Pos.CENTER);
        drawPlayersTurn(turnCount);
        rootGameInfo.getChildren().add(whoseTurn);
    }

    private void drawPlayersTurn(int turnCount) {
        Text playersTurn = new Text(setPlayerTurnText(turnCount) + "\n");
        playersTurn.setFont(HEADING);
        playersTurn.setFill(Color.ORANGERED);
        whoseTurn.getChildren().add(playersTurn);
    }

    private String setPlayerTurnText(int turnCount) {
        if (turnCount % 2 == 0) {
            return "It's the Eagle's turn!";
        } else {
            return "It's the Shark's turn!";
        }
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
        chosenPiece.setAlignment(Pos.CENTER);
        rootGameInfo.getChildren().add(chosenPiece);
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
        drawTurnCount(turnCount);
        drawScores(sharkScore, eagleScore);
    }

    private void clearView() {
        whoseTurn.getChildren().clear();
        scoreInfo.getChildren().clear();
        chosenPiece.getChildren().clear();
    }


    public void showError(String message) {
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

    //region Game Event
    @Override
    public void gameInitialised(String sharkPlayerName, String eaglePlayerName,
                                int turnCount, int totalTurns, double sharkScore, double eagleScore) {

        this.totalTurns = totalTurns;
        initTitleInfo(sharkPlayerName, eaglePlayerName);
        initWhoseTurn(turnCount);
        initScoreInfo(turnCount, sharkScore, eagleScore);
        initMoveList();
        initChosenPiece();
    }

    @Override
    public void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore) {
        updateGameInfo(turnCount, sharkScore, eagleScore);
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
