package main.java.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.ResPath;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 */
public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();

    public static SceneManager getInstance(){
        return INSTANCE;
    }

    private Stage stage;
    private BorderPane root;

    public void init(Stage stage){
        this.stage = stage;
        this.stage.show();

        initRoot();
    }

    private void initRoot(){
        root = new BorderPane();
        //menu
        //TODO: add a menu bar
        stage.setScene(new Scene(root));
    }

    public void showStartMenu(){
//        FXMLLoader startMenuLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.START_MENU));
//        try {
//            root.setCenter(startMenuLoader.load());
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void showGameView(){
        FXMLLoader gameLoader = new FXMLLoader(getClass().getClassLoader().getResource(ResPath.VIEW_GAME));
        try {
            stage.setScene(new Scene(gameLoader.load()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
