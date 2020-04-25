package main.java.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.ResPath;
import main.java.controller.GameViewController;
import main.java.view.EndGameView;
import main.java.view.HowToPlay;

import java.io.FileNotFoundException;
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
        initMenuBar();
    }

    private void initRoot() {
        root = new BorderPane();
        stage.setScene(new Scene(root));
    }

    public void initMenuBar() {
//        Game game = Game.getInstance();
        MenuBar menu = new MenuBar();
        Menu eagleVsShark = new Menu("Menu");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> {
//            if (game.getListener() != null) {
//                game.getListener().deleteTimer();
//                game.resetGame();
//            }
            showStartMenu();
        });
        MenuItem howTo = new MenuItem("How to play");
        howTo.setOnAction(event -> {
            try {
//                if (game.getListener() != null) {
//                    game.getListener().stopTimer();
//                }
                Stage howToStage = new HowToPlay();

//                howToStage.setOnHidden(e -> {
//                    if (game.getListener() != null) {
//                        game.getListener().startTimer();
//                    }
//                });

                howToStage.show();
            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            }
        });
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        eagleVsShark.getItems().addAll(newGame, howTo, quit);
        menu.getMenus().add(eagleVsShark);
        root.setTop(menu);
    }


    public void showStartMenu() {
        FXMLLoader startMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.START_MENU));
        try {
            root.setCenter(startMenuLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGameView(String sharkPlayerName, String eaglePlayerName, int timeLimit) {
        FXMLLoader gameLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.VIEW_GAME));
        try {
            root.setCenter(gameLoader.load());

            GameViewController controller = gameLoader.getController();
            controller.initGameData(sharkPlayerName, eaglePlayerName, timeLimit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEndGame(String winner) {
//        Game game = Game.getInstance();
//        if (game.getListener() != null) {
//            game.getListener().deleteTimer();
//        }
        Stage endGameStage = new EndGameView(winner);
        endGameStage.show();
    }

}
