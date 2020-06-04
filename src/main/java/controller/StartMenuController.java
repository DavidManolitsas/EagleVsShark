package main.java.controller;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import javafx.fxml.FXML;
import main.java.util.SceneManager;
import main.java.view.startGame.StartGameMenu;
import main.java.view.startGame.StartGameMenu.StartGameMenuListener;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 * @invariant 1. startMenu != null
 * 2. game != null
 */
public class StartMenuController
        implements StartGameMenuListener {

    @FXML
    private StartGameMenu startGameMenu;

    @FXML
    public void initialize() {
        startGameMenu.setStartMenuListener(this);
    }

    @Requires("sharkPlayerName != null && eaglePlayerName != null && timeLimit != null")
    @Ensures("!timeLimit.trim().isEmpty() && timeLimit >= 10 && !eaglePlayerName.trim().isEmpty() && !sharkPlayerName.trim().isEmpty()")
    @Override
    public void onStartBtClick(String sharkPlayerName, String eaglePlayerName) {
        //check all game details are correct
        if (sharkPlayerName.trim().isEmpty() || eaglePlayerName.trim().isEmpty()) {
            startGameMenu.showError("Enter both player names to start the game");
        } else {
            SceneManager.getInstance().showGameView(sharkPlayerName, eaglePlayerName);
        }
    }

    @Override
    public void onCreateCustomGameBtClick() {
        SceneManager.getInstance().showCustomGameMenu();
    }


    @Override
    public void onSinglePlayerClicked() {
        SceneManager.getInstance().showSinglePlayerMenu();
    }
}
