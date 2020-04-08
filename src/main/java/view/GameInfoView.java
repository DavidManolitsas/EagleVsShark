package main.java.view;

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
import main.java.model.Game;
import main.java.model.move.Move;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-31
 */
public class GameInfoView
        extends BorderPane {

    public interface GameInfoViewEventListener {
        void onMoveListItemClicked(Move move);

        void onMoveButtonClicked(Move move);
    }

    private GameInfoViewEventListener gameInfoViewEventListener;

    private static final Font TITLE = Font.font("Helvetica", 18);
    private static final Font BODY = Font.font("Helvetica", 14);
    private static final int TOTAL_SQUARES = 150;
    private ListView<Move> moveList;
    //TODO: is this right?
    private Game game;

    public GameInfoView() {
        game = new Game("John", "Smith");
        initMoveList();
        drawGameInfo();
    }

    public void drawGameInfo() {
        VBox gameInfo = new VBox();
        gameInfo.setPadding(new Insets(40, 20, 40, 20));
        gameInfo.setSpacing(10);
        gameInfo.setAlignment(Pos.CENTER);

        Text title = new Text("Eagle vs. Shark\n");
        title.setFont(TITLE);

        Text sharkPlayer = new Text(game.getSharkPlayer().getPlayerName() + " is the Shark Player");
        sharkPlayer.setFont(BODY);
        Text eaglePlayer = new Text(game.getEaglePlayer().getPlayerName() + " is the Eagle Player\n");
        eaglePlayer.setFont(BODY);

        Text playersTurn = new Text(setPlayerTurnText() + "\n");
        playersTurn.setFont(TITLE);
        playersTurn.setFill(Color.PURPLE);

        Text turnCountText = new Text("Turn No. " + game.getTurnCount());
        turnCountText.setFont(BODY);

        Text sharkScoreText = new Text("Shark Score: " + (game.getSharkSquareCount() / TOTAL_SQUARES) + "%");
        sharkScoreText.setFont(BODY);
        Text eagleScoreText = new Text("Eagle Score: " + (game.getEagleSquareCount() / TOTAL_SQUARES) + "%");
        eagleScoreText.setFont(BODY);

        gameInfo.getChildren()
                .addAll(title, sharkPlayer, eaglePlayer, playersTurn, turnCountText, sharkScoreText, eagleScoreText);
        this.setTop(gameInfo);

        // Set moveList
        this.setCenter(moveList);

        if (moveList != null) {
            Button moveBt = new Button("Move");
            moveBt.setWrapText(true);
            moveBt.setFont(TITLE);
            moveBt.setPrefWidth(250);

            moveBt.setOnAction(event -> {
                try {
                    getGameInfoViewEventListener().onMoveButtonClicked(getSelectedMove());
                } catch (NullPointerException e) {
                    showError("No move was selected");
                }
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
        drawGameInfo();
    }


    private Move getSelectedMove() throws NullPointerException {
        return moveList.getSelectionModel().getSelectedItem();
    }

    private String setPlayerTurnText() {
        if (game.getTurnCount() % 2 == 0) {
            return "It's " + game.getEaglePlayer().getPlayerName() + "'s turn!";
        } else {
            return "It's " + game.getSharkPlayer().getPlayerName() + "'s turn!";
        }
    }

    private void initMoveList() {
        moveList = new ListView<>();
        moveList.setFixedCellSize(50);

    }

    private void showError(String message) {
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
