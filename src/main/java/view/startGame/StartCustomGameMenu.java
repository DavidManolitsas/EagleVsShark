package main.java.view.startGame;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-05
 */
public class StartCustomGameMenu
        extends StartMenu {


    public interface StartCustomGameMenuListener {
        void onStartGameBtClick(String sharkPlayerName, String eaglePlayerName, String timeLimit, String rows,
                                String cols, String sharkCount, String eagleCount);
    }

    private StartCustomGameMenuListener startCustomGameMenuListener;


    public StartCustomGameMenu() {
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
    public void drawGameDetails() {
        //Shark Player
        Text sharkPlayerText = new Text("Shark Player: ");
        sharkPlayerText.setFont(BODY);
        TextField sharkPlayerNameField = new TextField();
        sharkPlayerNameField.setFont(BODY);
        sharkPlayerNameField.setAlignment(Pos.CENTER);
        //Eagle Player
        Text eaglePlayerText = new Text("Eagle Player: ");
        eaglePlayerText.setFont(BODY);
        TextField eaglePlayerNameField = new TextField();
        eaglePlayerNameField.setFont(BODY);
        eaglePlayerNameField.setAlignment(Pos.CENTER);

        //set time limit per turn
        Text timeLimitText = new Text("Time per turn (seconds): ");
        timeLimitText.setFont(BODY);
        TextField timeLimitField = new TextField();
        timeLimitField.setFont(BODY);
        timeLimitField.setAlignment(Pos.CENTER);

        //Board size
        Text enterBoard = new Text("Size of the Board:");
        enterBoard.setFont(BODY);
        HBox rowCol = new HBox();
        rowCol.setSpacing(2);
        rowCol.setAlignment(Pos.CENTER);
        TextField rowsText = new TextField();
        rowsText.setFont(BODY);
        rowsText.setPromptText("Rows");
        Text by = new Text(" x ");
        by.setFont(BODY);
        TextField colsText = new TextField();
        colsText.setFont(BODY);
        colsText.setPromptText("Columns");
        rowCol.getChildren().addAll(rowsText, by, colsText);

        Text enterPieces = new Text("Number of pieces:");
        enterPieces.setFont(BODY);
        HBox sharkEagle = new HBox();
        sharkEagle.setSpacing(5);
        sharkEagle.setAlignment(Pos.CENTER);
        TextField sharkField = new TextField();
        sharkField.setFont(BODY);
        sharkField.setPromptText("Sharks");
        TextField eagleField = new TextField();
        eagleField.setFont(BODY);
        eagleField.setPromptText("Eagles");
        sharkEagle.getChildren().addAll(sharkField, eagleField);

        //Start Game Button
        Button startBt = new Button("Start Game");
        startBt.setFont(BODY);
        startBt.setCursor(Cursor.HAND);
        startBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
        startBt.setPrefWidth(175);

        startBt.setOnAction(event -> {
            String eaglePlayerName = eaglePlayerNameField.getText();
            String sharkPlayerName = sharkPlayerNameField.getText();
            String timeLimit = timeLimitField.getText();
            String rows = rowsText.getText();
            String cols = colsText.getText();
            String sharkCount = sharkField.getText();
            String eagleCount = eagleField.getText();

            getCustomGameMenuListener()
                    .onStartGameBtClick(sharkPlayerName, eaglePlayerName, timeLimit, rows, cols, sharkCount,
                                        eagleCount);
        });

        //Menu
        VBox menuList = new VBox();
        menuList.setSpacing(15);
        menuList.setPadding(new Insets(30, 270, 180, 270));
        menuList.getChildren()
                .addAll(sharkPlayerText, sharkPlayerNameField, eaglePlayerText, eaglePlayerNameField, timeLimitText,
                        timeLimitField, enterBoard, rowCol, enterPieces, sharkEagle, startBt);

        getRoot().setCenter(menuList);
        menuList.setAlignment(Pos.CENTER);

    }


    public StartCustomGameMenuListener getCustomGameMenuListener() {
        return Objects.requireNonNull(this.startCustomGameMenuListener);
    }

    public void setStartCustomGameMenuListener(StartCustomGameMenuListener startCustomGameMenuListener) {
        if (startCustomGameMenuListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.startCustomGameMenuListener = startCustomGameMenuListener;
    }


}
