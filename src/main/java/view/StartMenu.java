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
import javafx.scene.text.Font;
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
        void onStartBtClick(String sharkPlayerName, String eaglePlayerName) throws IOException;
    }

    private StartMenuListener startMenuListener;
    private BorderPane root = new BorderPane();
    private static final Font TITLE = Font.font("Helvetica", 36);
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
        drawPlayerNames();

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
        Text title = new Text("\nEagle vs. Shark");
        title.setFont(TITLE);
        Text mango = new Text("Created by Mango");
        mango.setFont(BODY);
        titleBox.getChildren().addAll(title, mango);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(60,0,0,0));
        titleBox.setSpacing(10);
        root.setTop(titleBox);
    }

    private void drawPlayerNames() {
        Text sharkPlayerText = new Text("Shark Player: ");
        sharkPlayerText.setFont(BODY);
        TextField sharkPlayerNameField = new TextField();
        //Eagle Player
        Text eaglePlayerText = new Text("Eagle Player: ");
        eaglePlayerText.setFont(BODY);
        TextField eaglePlayerNameField = new TextField();

        //Start Game Button
        Button startBt = new Button("Start Game");
        startBt.setFont(BODY);
        startBt.setCursor(Cursor.HAND);
        startBt.setOnAction(event -> {

            String eaglePlayerName = eaglePlayerNameField.getText();
            String sharkPlayerName = sharkPlayerNameField.getText();
            try {
                getStartMenuListener().onStartBtClick(sharkPlayerName, eaglePlayerName);
            } catch (IOException e) {
                System.err.println("wrong!");
            }

        });

        //Menu
        VBox menuList = new VBox();
        menuList.setSpacing(20);
        menuList.setPadding(new Insets(0, 250, 10, 250));
        menuList.getChildren()
                .addAll(sharkPlayerText, sharkPlayerNameField, eaglePlayerText, eaglePlayerNameField, startBt);

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
        a.setContentText(message);
        a.show();
    }

}
