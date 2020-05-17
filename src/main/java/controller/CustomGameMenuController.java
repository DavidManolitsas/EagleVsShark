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
    public void onStartGameBtClick(String sharkPlayerName, String eaglePlayerName, String timeLimit, String turnNo,
                                   String rows,
                                   String cols, String sharkCount, String eagleCount) {
        if (sharkPlayerName.trim().isEmpty() || eaglePlayerName.trim().isEmpty()) {
            customGameMenu.showError("Enter both player names to start the game");
        } else if (timeLimit.trim().isEmpty()) {
            customGameMenu.showError("Enter a time limit to start the game");
        } else if (rows.trim().isEmpty() || cols.trim().isEmpty()) {
            customGameMenu.showError("Enter a number of rows and columns to start the game");
        } else if (sharkCount.trim().isEmpty() || eagleCount.trim().isEmpty()) {
            customGameMenu.showError("Enter a number of shark and eagle pieces");
        } else if (turnNo.trim().isEmpty()) {
            customGameMenu.showError("Enter a number of turns");
        } else {
            try {
                int turnTime = Integer.parseInt(timeLimit);
                int turnCount = Integer.parseInt(turnNo);
                int row = Integer.parseInt(rows);
                int col = Integer.parseInt(cols);
                int sharks = Integer.parseInt(sharkCount);
                int eagles = Integer.parseInt(eagleCount);

                if (turnTime < 10) {
                    customGameMenu.showError("The minimum time limit is 10 seconds");
                } else if (turnCount < 10) {
                    customGameMenu.showError("Turn count must be 10 or more");
                } else if (row < 6 || col < 6) {
                    customGameMenu.showError("Enter a row or column of 6 or higher");
                } else if (sharks < 3 || eagles < 3) {
                    customGameMenu.showError("Minimum number of each piece type is 3");
                } else if (sharks > col / 2 || eagles > col / 2) {
                    customGameMenu.showError("Maximum number of each piece type is half the number of columns");
                } else {
                    SceneManager.getInstance()
                                .showCustomGameView(sharkPlayerName, eaglePlayerName, turnTime, turnCount, row, col,
                                                    sharks,
                                                    eagles);
                }
            } catch (NumberFormatException e) {
                customGameMenu.showError(
                        "Enter a number for the time limit, rows and columns and number of pieces to start the game");
            }
        }
    }
}
