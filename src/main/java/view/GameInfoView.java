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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.java.model.Game.GameModelEventListener;
import main.java.model.move.Move;

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

    private static final Font TITLE = Font.font("Helvetica", 18);
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
            Button moveBt = new Button("Move");
            moveBt.setWrapText(true);
            moveBt.setFont(TITLE);
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
        drawTitle();
        drawPlayerNames(sharkPlayerName, eaglePlayerName);
        rootGameInfo.getChildren().add(titleInfo);
    }

    public void drawTitle() {
        Text title = new Text("Eagle vs. Shark\n");
        title.setFont(TITLE);
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
        drawPlayersTurn(turnCount);
        rootGameInfo.getChildren().add(whoseTurn);
    }

    private void drawPlayersTurn(int turnCount) {
        Text playersTurn = new Text(setPlayerTurnText(turnCount) + "\n");
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
        drawTurnCount(turnCount);
        drawScores(sharkScore, eagleScore);

        rootGameInfo.getChildren().add(scoreInfo);
    }

    public void drawTurnCount(int turnCount) {
        Text turnCountText = new Text("Turn No. " + turnCount + "/" + totalTurns);
        scoreInfo.getChildren().add(turnCountText);
    }

    private void drawScores(double sharkScore, double eagleScore) {
        Text sharkScoreText = new Text("Shark Score: " + decimalFormat.format(sharkScore));
        Text eagleScoreText = new Text("Eagle Score: " + decimalFormat.format(eagleScore));
        scoreInfo.getChildren().addAll(sharkScoreText, eagleScoreText);
    }
    //end region


    private void updateGameInfo(int turnCount, double sharkScore, double eagleScore) {
        clearView();
        drawPlayersTurn(turnCount);
        drawTurnCount(turnCount);
        drawScores(sharkScore, eagleScore);
        System.out.println(sharkScore + " " + eagleScore);
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
