package main.java.view;


import java.io.IOException;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.java.ResPath;
import main.java.util.SceneManager;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 */


public class StartMenu
        extends BorderPane {

    public interface StartMenuListener {
        void onStartBtClick(String sharkPlayerName, String eaglePlayerName, String timeLimit) throws IOException;
    }

    private StartMenuListener startMenuListener;
    private BorderPane root = new BorderPane();
    private static final Font TITLE = Font.font("Impact", FontWeight.BOLD, 50);
    private static final Font BODY = Font.font("Helvetica", 16);


    public StartMenu() {
        setCenter(drawMenu());
    }

    public BorderPane drawMenu() {
        //Background
        drawBackground();
        //Title
        drawTitle();
        //Players and Start Game
        drawGameDetails();

        return root;
    }


    private void drawBackground() {
        Image image = new Image(ResPath.START_MENU_BACKGROUND);
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
        root.setBackground(background);
    }

    private void drawTitle() {
        VBox titleBox = new VBox();
        Text title = new Text("Eagle vs. Shark");
        title.setFont(TITLE);
        title.setFill(Color.ORANGERED);
        Text mango = new Text("Created by Mango");
        mango.setFont(BODY);
        mango.setFill(Color.ORANGERED);
        titleBox.getChildren().addAll(title, mango);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(210, 0, 0, 0));
        titleBox.setSpacing(10);
        root.setTop(titleBox);
    }

    private void drawGameDetails() {
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

        //Start Game Button
        Button startBt = new Button("Start Game");
        startBt.setFont(BODY);
        startBt.setCursor(Cursor.HAND);
        startBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
        startBt.setOnAction(event -> {

            String eaglePlayerName = eaglePlayerNameField.getText();
            String sharkPlayerName = sharkPlayerNameField.getText();
            String timeLimit = timeLimitField.getText();
            try {
                getStartMenuListener().onStartBtClick(sharkPlayerName, eaglePlayerName, timeLimit);
            } catch (IOException e) {
                System.err.println("wrong!");
            }

        });

        //Menu
        VBox menuList = new VBox();
        menuList.setSpacing(20);
        menuList.setPadding(new Insets(0, 270, 180, 270));
        menuList.getChildren()
                .addAll(sharkPlayerText, sharkPlayerNameField, eaglePlayerText, eaglePlayerNameField, timeLimitText,
                        timeLimitField, startBt);

        root.setCenter(menuList);
        menuList.setAlignment(Pos.CENTER);
    }

    public void startGame() {
        SceneManager.getInstance().showGameView();
    }

    public StartMenuListener getStartMenuListener() {
        return Objects.requireNonNull(this.startMenuListener);
    }

    public void setStartMenuListener(StartMenuListener startMenuListener) {
        if (startMenuListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.startMenuListener = startMenuListener;
    }

    public void showError(String message) {
        Alert a = new Alert(AlertType.ERROR);
        a.setHeaderText("Game Setup Incorrect");
        a.setContentText(message);
        a.show();
    }

}
