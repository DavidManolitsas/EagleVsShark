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
    private VBox gameInfo = new VBox();
    private Text playersTurn;
    private Text turnCountText;
    private Text sharkScoreText;
    private Text eagleScoreText;



    public GameInfoView() {
        initGameInfo();
        initMoveList();
        drawMoveList();
    }

    private void drawMoveList() {
        // Set moveList
        this.setCenter(moveList);

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

    private void drawGameInfo(int turnCount, double sharkScore, double eagleScore) {
        playersTurn.setText(setPlayerTurnText(turnCount) + "\n");
        turnCountText.setText("Turn No. " + turnCount + "/" + totalTurns);
        sharkScoreText.setText("Shark Score: " + decimalFormat.format(sharkScore));
        eagleScoreText.setText("Eagle Score: " + decimalFormat.format(eagleScore));
    }

    public void showPlayerNames(String sharkPlayerName, String eaglePlayerName) {
        Text sharkPlayer = new Text(sharkPlayerName + " is the Shark Player");
        sharkPlayer.setFont(BODY);
        Text eaglePlayer = new Text(eaglePlayerName + " is the Eagle Player\n");
        eaglePlayer.setFont(BODY);
        gameInfo.getChildren()
                .addAll(sharkPlayer, eaglePlayer, playersTurn, turnCountText, sharkScoreText, eagleScoreText);
    }


    private String setPlayerTurnText(int turnCount) {
        if (turnCount % 2 == 0) {
            return "It's the Eagle's turn!";
        } else {
            return "It's the Shark's turn!";
        }
    }

    private void initMoveList() {
        moveList = new ListView<>();
        moveList.setFixedCellSize(50);
    }

    private void initGameInfo() {
        gameInfo.setPadding(new Insets(40, 20, 40, 20));
        gameInfo.setSpacing(10);
        gameInfo.setAlignment(Pos.CENTER);

        Text title = new Text("Eagle vs. Shark\n");
        title.setFont(TITLE);
        gameInfo.getChildren().add(title);

        playersTurn = new Text();
        playersTurn.setFont(TITLE);
        playersTurn.setFill(Color.PURPLE);

        turnCountText = new Text();
        turnCountText.setFont(BODY);

        sharkScoreText = new Text();
        sharkScoreText.setFont(BODY);

        eagleScoreText = new Text();
        eagleScoreText.setFont(BODY);

        this.setTop(gameInfo);
    }

    public void showError(String message) {
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

    //region Game Event
    @Override
    public void gameInitialised(String eaglePlayerName, String sharkPlayerName,
                                int turnCount, int totalTurns, double sharkScore, double eagleScore) {
        this.totalTurns = totalTurns;
        showPlayerNames(eaglePlayerName, sharkPlayerName);
        drawGameInfo(turnCount, sharkScore, eagleScore);
    }

    @Override
    public void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore) {
        drawGameInfo(turnCount, sharkScore, eagleScore);
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
