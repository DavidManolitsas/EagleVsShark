package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class GameViewController {

    @FXML
    private Label lbHelloWorld;

    @FXML
    public void initialize() {
        lbHelloWorld.setText("Hey Yo");
    }
}
