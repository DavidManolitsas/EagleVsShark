package main.java.util;

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
        MenuBar menu = new MenuBar();
        Menu eagleVsShark = new Menu("Menu");
        MenuItem howTo = new MenuItem("How to play");
        howTo.setOnAction(event -> {
            Stage howToStage = new HowToPlay();
            howToStage.show();
        });
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> {
            Platform.exit();
        });
        eagleVsShark.getItems().addAll(howTo, quit);
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

}