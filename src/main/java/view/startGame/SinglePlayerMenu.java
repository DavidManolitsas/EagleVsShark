package main.java.view.startGame;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-25
 */
public class SinglePlayerMenu
        extends StartMenu {

    public interface SinglePlayerMenuListener {
        void onStartSinglePlayerGame(String playerName);
    }

    private SinglePlayerMenuListener singlePlayerMenuListener;

    public SinglePlayerMenu() {
        setCenter(drawMenu());
    }


    @Override
    protected BorderPane drawMenu() {
        //Background
        drawBackground();
        //Title
        drawTitle();
        //Players and Start Game
        drawGameDetails();

        return getRoot();
    }


    @Override
    protected void drawGameDetails() {
        VBox menuList = new VBox();
        menuList.setSpacing(20);
        menuList.setPadding(new Insets(60, 270, 180, 270));
        menuList.setAlignment(Pos.CENTER);

        Text enterPlayerNameText = new Text("Enter Player Name: ");
        enterPlayerNameText.setFont(BODY);
        TextField playerNameField = new TextField();
        playerNameField.setFont(BODY);
        playerNameField.setAlignment(Pos.CENTER);

        Button startBt = new Button("Start Game");
        startBt.setFont(BODY);
        startBt.setCursor(Cursor.HAND);
        startBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
        startBt.setPrefWidth(180);

        startBt.setOnAction(event -> {
            String playerName = playerNameField.getText();
            getSinglePlayerMenuListener().onStartSinglePlayerGame(playerName);
        });

        menuList.getChildren().addAll(enterPlayerNameText, playerNameField, startBt);
        getRoot().setCenter(menuList);
    }


    public SinglePlayerMenuListener getSinglePlayerMenuListener() {
        return Objects.requireNonNull(this.singlePlayerMenuListener);
    }

    public void setStartCustomGameMenuListener(SinglePlayerMenuListener singlePlayerMenuListener) {
        if (singlePlayerMenuListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.singlePlayerMenuListener = singlePlayerMenuListener;
    }

}
