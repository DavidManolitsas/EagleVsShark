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

    private ListView<Move> moveList = new ListView<>();

    //TODO: place holders
    private String sharkPlayerName = "John";
    private String eaglePlayerName = "Smith";
    private int sharkPlayerScore = 0;
    private int eaglePlayerScore = 0;
    private int turnCount = 1;


    public GameInfoView() {
        drawGameInfo();
    }

    public void drawGameInfo() {
        VBox gameInfo = new VBox();
        gameInfo.setPadding(new Insets(40, 20, 40, 20));
        gameInfo.setSpacing(10);
        gameInfo.setAlignment(Pos.CENTER);

        Text title = new Text("Eagle vs. Shark\n");
        title.setFont(TITLE);

        Text sharkPlayer = new Text(sharkPlayerName + " is the Shark Player");
        sharkPlayer.setFont(BODY);
        Text eaglePlayer = new Text(eaglePlayerName + " is the Eagle Player\n");
        eaglePlayer.setFont(BODY);

        Text playersTurn = new Text(setPlayerTurnText() + "\n");
        playersTurn.setFont(TITLE);
        playersTurn.setFill(Color.PURPLE);

        Text turnCountText = new Text("Turn No. " + turnCount);
        turnCountText.setFont(BODY);

        Text sharkScoreText = new Text("Shark Score: " + (sharkPlayerScore / 150) + "%");
        sharkScoreText.setFont(BODY);
        Text eagleScoreText = new Text("Eagle Score: " + (eaglePlayerScore / 150) + "%");
        eagleScoreText.setFont(BODY);

        gameInfo.getChildren()
                .addAll(title, sharkPlayer, eaglePlayer, playersTurn, turnCountText, sharkScoreText, eagleScoreText);
        this.setTop(gameInfo);

        //TODO: PLACEHOLDER this is for testing change to type move
        //This would be the list of valid moves
//        String[] moves = {"Move 1", "Move 2", "Move 3", "Move 4", "Move 5", "Move 6", "Move 7"};
//        ArrayList<BorderPane> moveList = new ArrayList<>();
//
//        for (int i = 0; i < moves.length; i++) {
//            BorderPane pane = new BorderPane();
//            pane.setPadding(new Insets(30, 20, 30, 20));
//            Text text = new Text(moves[i]);
//            pane.setCenter(text);
//            moveList.add(pane);
//        }
//
//        ListView<BorderPane> list = new ListView<BorderPane>();
//        //set items
//        ObservableList<BorderPane> items = FXCollections.observableArrayList(moveList);
//        list.setItems(items);
//        root.setCenter(list);
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
                }
            }
        });

        //redraw game info
        drawGameInfo();
    }

    private Move getSelectedMove() throws NullPointerException {
        return moveList.getSelectionModel().getSelectedItem();
    }

    public String setPlayerTurnText() {
        int x = 1;
        if (x == 1) {
            return "It's " + sharkPlayerName + "'s turn!";
        } else {
            return "It's" + eaglePlayerName + "'s turn!";
        }
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
