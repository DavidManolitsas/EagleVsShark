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
 * @date 2020-03-30
 */


public class StartGameMenu
        extends StartMenu {

    public interface StartGameMenuListener {
        void onStartBtClick(String sharkPlayerName, String eaglePlayerName);

        void onCreateCustomGameBtClick();
    }

    private StartGameMenuListener startMenuListener;

    public StartGameMenu() {
        setCenter(drawMenu());
    }

    @Override
    public BorderPane drawMenu() {
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


        //Start Game Button
        Button startBt = new Button("Start Game");
        startBt.setFont(BODY);
        startBt.setCursor(Cursor.HAND);
        startBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
        startBt.setPrefWidth(180);
        startBt.setOnAction(event -> {

            String eaglePlayerName = eaglePlayerNameField.getText();
            String sharkPlayerName = sharkPlayerNameField.getText();

            getStartMenuListener().onStartBtClick(sharkPlayerName, eaglePlayerName);
        });

        //Custom Game Start Button
        Button customStartBt = new Button("Create Custom Game");
        customStartBt.setFont(BODY);
        customStartBt.setCursor(Cursor.HAND);
        customStartBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
        customStartBt.setPrefWidth(180);

        customStartBt.setOnAction(event -> {
            getStartMenuListener().onCreateCustomGameBtClick();
        });


        //Menu
        VBox menuList = new VBox();
        menuList.setSpacing(20);
        menuList.setPadding(new Insets(60, 270, 180, 270));
        menuList.getChildren()
                .addAll(sharkPlayerText, sharkPlayerNameField, eaglePlayerText, eaglePlayerNameField, startBt,
                        customStartBt);

        getRoot().setCenter(menuList);
        menuList.setAlignment(Pos.CENTER);
    }


    public StartGameMenuListener getStartMenuListener() {
        return Objects.requireNonNull(this.startMenuListener);
    }

    public void setStartMenuListener(StartGameMenuListener startMenuListener) {
        if (startMenuListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.startMenuListener = startMenuListener;
    }


}
