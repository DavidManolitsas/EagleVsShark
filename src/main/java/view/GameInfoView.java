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


    @Override
    public void updateGameInfo(int turnCount, double sharkScore, double eagleScore) {
        drawGameInfo(turnCount, sharkScore, eagleScore);
    }

    public interface GameInfoViewEventListener {
        void onMoveListItemClicked(Move move);

        void onMoveButtonClicked(Move move);
    }

    private GameInfoViewEventListener gameInfoViewEventListener;

    private static final Font TITLE = Font.font("Helvetica", 18);
    private static final Font BODY = Font.font("Helvetica", 14);
    private ListView<Move> moveList;
    private VBox gameInfo = new VBox();
    private DecimalFormat decimalFormat = new DecimalFormat("#%");


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

        //
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
        Text playersTurn = new Text(setPlayerTurnText(turnCount) + "\n");
        playersTurn.setFont(TITLE);
        playersTurn.setFill(Color.PURPLE);

        Text turnCountText = new Text("Turn No. " + turnCount);
        turnCountText.setFont(BODY);


        System.out.println(sharkScore);

        Text sharkScoreText = new Text("Shark Score: " + decimalFormat.format(sharkScore));
        sharkScoreText.setFont(BODY);
        Text eagleScoreText = new Text("Eagle Score: " + decimalFormat.format(eagleScore));
        eagleScoreText.setFont(BODY);

        gameInfo.getChildren().addAll(playersTurn, turnCountText, sharkScoreText, eagleScoreText);

    }

    public void showPlayerNames(String eaglePlayerName, String sharkPlayerName) {

        Text sharkPlayer = new Text(sharkPlayerName + " is the Shark Player");
        sharkPlayer.setFont(BODY);
        Text eaglePlayer = new Text(eaglePlayerName + " is the Eagle Player\n");
        eaglePlayer.setFont(BODY);
        gameInfo.getChildren().addAll(sharkPlayer, eaglePlayer);
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
        this.setTop(gameInfo);
    }

    public void showError(String message) {
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

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
