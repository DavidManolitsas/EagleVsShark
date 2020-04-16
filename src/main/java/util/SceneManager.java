package main.java.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.ResPath;
import main.java.model.Game;
import main.java.view.EndGameView;
import main.java.view.HowToPlay;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 */
public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    private Stage stage;
    private BorderPane root;

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
        Game game = Game.getInstance();
        MenuBar menu = new MenuBar();
        Menu eagleVsShark = new Menu("Menu");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> {
            if (game.getListener() != null) {
                game.getListener().deleteTimer();
            }
            showStartMenu();
        });
        MenuItem howTo = new MenuItem("How to play");
        howTo.setOnAction(event -> {
            try {
                if (game.getListener() != null) {
                    game.getListener().stopTimer();
                }
                Stage howToStage = new HowToPlay();

                howToStage.setOnHidden(e -> {
                    if (game.getListener() != null) {
                        game.getListener().startTimer();
                    }
                });

                howToStage.show();
            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            }
        });
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> {
            Platform.exit();
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

    public void showGameView() {
        FXMLLoader gameLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.VIEW_GAME));
        try {
            root.setCenter(gameLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEndGame(String winner) {
        Game game = Game.getInstance();
        if (game.getListener() != null) {
            game.getListener().deleteTimer();
        }
        Stage endGameStage = new EndGameView(winner);
        endGameStage.show();
    }

}
