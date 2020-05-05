package main.java.controller;

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

    /**
     * When the player clicks the "start game" button. Set the player names and the amount of time
     * per turn that the player selected
     *
     * @param sharkPlayerName
     *         the shark players name
     * @param eaglePlayerName
     *         the eagle players name
     *
     * @require 1. sharkPlayerName != null
     * 2. eaglePlayerName != null
     * 3. timeLimit != null
     * @ensures 1. !timeLimit.trim().isEmpty()
     * 2. timeLimit >= 10
     * 3. !eaglePlayerName.trim().isEmpty() && !sharkPlayerName.trim().isEmpty()
     */
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
}
