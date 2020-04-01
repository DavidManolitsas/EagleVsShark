package main.java.controller;

import java.io.IOException;

import javafx.fxml.FXML;
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

    @FXML
    public void initialize() {
        startMenu.setStartMenuListener(this);
    }

    @Override
    public void onStartBtClick() throws IOException {
        startMenu.startGame();
    }
}
