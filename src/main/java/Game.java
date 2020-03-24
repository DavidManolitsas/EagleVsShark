package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    private void initGameWindow(Stage primaryStage) throws IOException {
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setTitle(APP_NAME);

        FXMLLoader boardViewLoader =
                new FXMLLoader(getClass().getClassLoader().getResource(ResPath.VIEW_GAME));
        primaryStage.setScene(new Scene(boardViewLoader.load()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
