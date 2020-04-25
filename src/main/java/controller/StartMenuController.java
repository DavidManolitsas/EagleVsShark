package main.java.controller;

import javafx.fxml.FXML;
import main.java.util.SceneManager;
import main.java.view.StartMenu;
import main.java.view.StartMenu.StartMenuListener;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 * @invariant 1. startMenu != null
 * 2. game != null
 */
public class StartMenuController
        implements StartMenuListener {

    @FXML
    private StartMenu startMenu;

    @FXML
    public void initialize() {
        startMenu.setStartMenuListener(this);
    }

    /**
     * When the player clicks the "start game" button. Set the player names and the amount of time
     * per turn that the player selected
     *
     * @param sharkPlayerName
     *         the shark players name
     * @param eaglePlayerName
     *         the eagle players name
     * @param timeLimit
     *         amount of time per turn
     *
     * @require 1. sharkPlayerName != null
     * 2. eaglePlayerName != null
     * 3. timeLimit != null
     * @ensures 1. !timeLimit.trim().isEmpty()
     * 2. timeLimit >= 10
     * 3. !eaglePlayerName.trim().isEmpty() && !sharkPlayerName.trim().isEmpty()
     */
    @Override
    public void onStartBtClick(String sharkPlayerName, String eaglePlayerName, String timeLimit) {
        //check all game details are correct
        if (sharkPlayerName.trim().isEmpty() || eaglePlayerName.trim().isEmpty()) {
            startMenu.showError("Enter both player names to start the game");
        } else if (timeLimit.trim().isEmpty()) {
            startMenu.showError("Enter a time limit to start the game");
        } else {
            try {
                int turnTime = Integer.parseInt(timeLimit);
                if (turnTime < 10) {
                    startMenu.showError("Set a time limit of 10 seconds or more");
                } else {
                    SceneManager.getInstance().showGameView(
                            sharkPlayerName, eaglePlayerName,
                            turnTime
                    );
                }
            } catch (NumberFormatException e) {
                startMenu.showError("Enter a number as the time limit to continue");
            }
        }
    }
}
