package main.java.controller;

import javafx.fxml.FXML;
import main.java.model.Game;
import main.java.view.StartMenu;
import main.java.view.StartMenu.StartMenuListener;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 */
public class StartMenuController
        implements StartMenuListener {

    @FXML
    private StartMenu startMenu;

    private Game game;

    @FXML
    public void initialize() {
        startMenu.setStartMenuListener(this);
    }


    @Override
    public void onStartBtClick(String sharkPlayerName, String eaglePlayerName, String timeLimit) {
        //check all game details are correct
        if (sharkPlayerName.trim().isEmpty() || eaglePlayerName.trim().isEmpty()) {
            startMenu.showError("Enter both player names to start the game");
        } else if (timeLimit.trim().isEmpty()) {
            startMenu.showError("Enter a time limit to start the game");
        } else {
            try {
                game = Game.getInstance();
                int turnTime = Integer.parseInt(timeLimit);
                game.setTurnTime(turnTime);
                game.initPlayers(sharkPlayerName, eaglePlayerName);
                startMenu.startGame();
            } catch (NumberFormatException e) {
                startMenu.showError("Enter a number as the time limit to continue");
            }

        }
    }
}
