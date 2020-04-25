package main.java.view;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-30
 */


public class MenuView
        extends MenuBar {

    public interface MenuBarEventListener {
        void onNewGameClicked();

        void onHowToClicked();

        void onHowToWindowClosed();

        void onQuitClicked();
    }

    private MenuBarEventListener listener;

    public MenuView() {
        super();
        Menu eagleVsShark = new Menu("Menu");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> {
            listener.onNewGameClicked();
        });

        MenuItem howTo = new MenuItem("How to play");
        howTo.setOnAction(event -> {
            listener.onHowToClicked();

            try {
                Stage howToStage = new HowToPlay();

                howToStage.setOnHidden(e -> {
                    listener.onHowToWindowClosed();
                });

                howToStage.show();
            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            }
        });
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> {
            listener.onQuitClicked();
        });
        eagleVsShark.getItems().addAll(newGame, howTo, quit);
        getMenus().add(eagleVsShark);
    }

    public MenuBarEventListener getListener() {
        return listener;
    }

    public void setListener(MenuBarEventListener listener) {
        if (listener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }
        this.listener = listener;
    }
}
