package main.java.view;


import java.io.IOException;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.java.util.SceneManager;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 */



public class StartMenu extends BorderPane {

    public interface StartMenuListener {
        void onStartBtClick() throws IOException;
    }

    private StartMenuListener startMenuListener;
    private String sharkPlayerName;
    private String eaglePlayerName;
    private static final Font TITLE = Font.font("Helvetica", 24);
    private static final Font BODY = Font.font("Helvetica", 14);

    public StartMenu(){
        setCenter(drawMenu());
    }

    public BorderPane drawMenu(){
        BorderPane root = new BorderPane();
        //Title
        Text title = new Text("Eagle vs. Shark\n\n");
        title.setFont(TITLE);
        //Shark Player
        Text sharkPlayerText = new Text("Shark Player: ");
        sharkPlayerText.setFont(BODY);
        TextField sharkPlayerNameField = new TextField();
        sharkPlayerName = sharkPlayerNameField.getText();

        //Eagle Player
        Text eaglePlayerText = new Text("Eagle Player: ");
        eaglePlayerText.setFont(BODY);
        TextField eaglePlayerNameField = new TextField();
        eaglePlayerName = eaglePlayerNameField.getText();

        //Start Game Button
        Text startBt = new Text("START GAME");
        startBt.setFont(BODY);
        startBt.setCursor(Cursor.HAND);
        startBt.setOnMouseClicked(event -> {
            try {
                getStartMenuListener().onStartBtClick();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Menu
        VBox menuList = new VBox();
        menuList.setSpacing(20);
        menuList.setPadding(new Insets(10,200,10,200));
        menuList.getChildren().addAll(title, sharkPlayerText, sharkPlayerNameField,  eaglePlayerText, eaglePlayerNameField, startBt);

        root.setCenter(menuList);
        menuList.setAlignment(Pos.CENTER);

        return root;
    }

    public void startGame() {
        SceneManager.getInstance().showGameView();
    }

    public StartMenuListener getStartMenuListener(){
        return Objects.requireNonNull(this.startMenuListener);
    }

    public void setStartMenuListener(StartMenuListener startMenuListener) {
        if (startMenuListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.startMenuListener = startMenuListener;
    }

    public String getSharkPlayerName() {
        return sharkPlayerName;
    }

    public String getEaglePlayerName() {
        return eaglePlayerName;
    }
}
