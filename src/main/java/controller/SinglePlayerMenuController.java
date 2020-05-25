package main.java.controller;

import javafx.fxml.FXML;
import main.java.view.startGame.SinglePlayerMenu;
import main.java.view.startGame.SinglePlayerMenu.SinglePlayerMenuListener;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-25
 */
public class SinglePlayerMenuController
        implements SinglePlayerMenuListener {

    @FXML
    private SinglePlayerMenu singlePlayerMenu;

    @FXML
    public void initialize() {
        singlePlayerMenu.setStartCustomGameMenuListener(this);
    }

    @Override
    public void onStartSinglePlayerGame(String playerName) {
        if (playerName.trim().isEmpty()) {
            singlePlayerMenu.showError("Please enter a name");
        } else {
            //TODO: Implement me
            System.out.println("Single Player Game Started! as " + playerName);
        }
    }

}
