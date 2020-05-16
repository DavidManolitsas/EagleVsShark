package main.java.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.ResPath;
import main.java.controller.GameViewController;
import main.java.model.Game.GameBuilder;
import main.java.view.EndGameView;

import java.io.IOException;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 */
public class SceneManager {


    private static SceneManager instance;

    private Stage stage;
    private BorderPane root;

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }

        return instance;
    }

    private SceneManager() {

    }

    public void init(Stage stage) {
        this.stage = stage;
        this.stage.show();

        initRoot();
    }

    private void initRoot() {
        root = new BorderPane();
        stage.setScene(new Scene(root));
    }

    public void showStartMenu() {
        FXMLLoader startMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.START_MENU));
        try {
            root.setCenter(startMenuLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showCustomGameMenu() {
        FXMLLoader customGameMenuLoader =
                new FXMLLoader(getClass().getClassLoader().getResource(ResPath.CUSTOM_GAME_MENU));
        try {
            root.setCenter(customGameMenuLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showGameView(String sharkPlayerName, String eaglePlayerName) {
        FXMLLoader gameLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.VIEW_GAME));
        try {
            root.setCenter(gameLoader.load());

            GameViewController controller = gameLoader.getController();
            controller.initGameData(new GameBuilder(sharkPlayerName, eaglePlayerName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCustomGameView(String sharkPlayerName, String eaglePlayerName,
                                   int timeLimit, int turnCount,
                                   int cols, int rows,
                                   int sharks, int eagles) {
        FXMLLoader gameLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.VIEW_GAME));
        try {
            root.setCenter(gameLoader.load());

            GameViewController controller = gameLoader.getController();
            controller.initGameData(new GameBuilder(sharkPlayerName, eaglePlayerName)
                                            .setTimeLimit(timeLimit)
                                            .setTurnCount(turnCount)
                                            .setRows(rows)
                                            .setCols(cols)
                                            .setSharkNums(sharks)
                                            .setEagleNums(eagles));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEndGame(String winner, double sharkScore, double eagleScore) {
        Stage endGameStage = new EndGameView(winner, sharkScore, eagleScore);
        endGameStage.show();
    }

}
