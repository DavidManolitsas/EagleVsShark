package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.util.SceneManager;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class Game
        extends Application {

    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 900;
    private static final String APP_NAME = "Eagle vs Shark";

    @Override
    public void start(Stage primaryStage) throws Exception {
        initGameWindow(primaryStage);

        primaryStage.show();
    }

    private void initGameWindow(Stage primaryStage) {
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setTitle(APP_NAME);

        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.init(primaryStage);
        sceneManager.showStartMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
