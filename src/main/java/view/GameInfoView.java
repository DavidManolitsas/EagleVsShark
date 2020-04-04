package main.java.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.java.model.move.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    //TODO: place holders
    private String sharkPlayerName = "John";
    private String eaglePlayerName = "Smith";
    private int sharkPlayerScore = 0;
    private int eaglePlayerScore = 0;
    private int turnCount = 1;


    public GameInfoView() {
        setCenter(drawGameInfo());
    }

    public BorderPane drawGameInfo() {
        BorderPane root = new BorderPane();

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
        root.setTop(gameInfo);

        //TODO: PLACEHOLDER this is for testing change to type move
        //This would be the list of valid moves
        String[] moves = {"Move 1", "Move 2", "Move 3", "Move 4", "Move 5", "Move 6", "Move 7"};
        ArrayList<BorderPane> moveList = new ArrayList<>();

        for (int i = 0; i < moves.length; i++) {
            BorderPane pane = new BorderPane();
            pane.setPadding(new Insets(30, 20, 30, 20));
            Text text = new Text(moves[i]);
            pane.setCenter(text);
            moveList.add(pane);
        }

        ListView<BorderPane> list = new ListView<BorderPane>();
        //set items
        ObservableList<BorderPane> items = FXCollections.observableArrayList(moveList);
        list.setItems(items);
        root.setCenter(list);

        Button moveBt = new Button("Move");
        moveBt.setWrapText(true);
        moveBt.setFont(TITLE);
        moveBt.setPrefWidth(250);
        root.setBottom(moveBt);
        return root;
    }

    public void showValidMoveList(List<Move> moveList) {
        // TODO: Implementation
    }

    public String setPlayerTurnText() {
        int x = 1;
        if (x == 1) {
            return "It's " + sharkPlayerName + "'s turn!";
        } else {
            return "It's" + eaglePlayerName + "'s turn!";
        }
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
