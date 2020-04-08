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
    public void onStartBtClick(String sharkPlayerName, String eaglePlayerName) {
        game = Game.getInstance();
        game.setPlayers(sharkPlayerName, eaglePlayerName);
        startMenu.startGame();
    }
}
