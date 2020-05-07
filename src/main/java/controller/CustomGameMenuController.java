package main.java.controller;

import javafx.fxml.FXML;
import main.java.util.SceneManager;
import main.java.view.startGame.StartCustomGameMenu;
import main.java.view.startGame.StartCustomGameMenu.StartCustomGameMenuListener;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-05
 */
public class CustomGameMenuController
        implements StartCustomGameMenuListener {

    @FXML
    public StartCustomGameMenu customGameMenu;

    @FXML
    public void initialize() {
        customGameMenu.setStartCustomGameMenuListener(this);
    }


    @Override
    public void onStartGameBtClick(String sharkPlayerName, String eaglePlayerName, String timeLimit, String rows,
                                   String cols) {
        if (sharkPlayerName.trim().isEmpty() || eaglePlayerName.trim().isEmpty()) {
            customGameMenu.showError("Enter both player names to start the game");
        } else if (timeLimit.trim().isEmpty()) {
            customGameMenu.showError("Enter a time limit to start the game");
        } else if (rows.trim().isEmpty() || cols.trim().isEmpty()) {
            customGameMenu.showError("Enter a number of rows and columns to start the game");
        } else {

            try {
                int turnTime = Integer.parseInt(timeLimit);
                int row = Integer.parseInt(rows);
                int col = Integer.parseInt(cols);

                if (turnTime < 10) {
                    customGameMenu.showError("The minimum time limit is 10 seconds");
                } else if (row < 7 || col < 7) {
                    customGameMenu.showError("Enter a row or column of 7 or higher");
                } else {
                    SceneManager.getInstance().showCustomGameView(sharkPlayerName, eaglePlayerName, turnTime, row, col);
                }

            } catch (NumberFormatException e) {
                customGameMenu.showError(
                        "Enter a number for the time limit as well as the rows and columns of the board size to " +
                                "continue");
            }
        }
    }
}