package main.java.view.startGame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.java.ResPath;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-05
 */
public abstract class StartMenu
        extends BorderPane {

    private BorderPane root = new BorderPane();
    static final Font TITLE = Font.font("Impact", FontWeight.BOLD, 50);
    static final Font BODY = Font.font("Helvetica", 16);

    protected abstract void drawGameDetails();

    protected abstract BorderPane drawMenu();

    void drawBackground() {
        Image image = new Image(ResPath.START_MENU_BACKGROUND);
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(
                new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER, size));
        root.setBackground(background);
    }

    void drawTitle() {
        VBox titleBox = new VBox();
        Text title = new Text("Eagle vs. Shark");
        title.setFont(TITLE);
        title.setFill(Color.ORANGERED);
        Text mango = new Text("Created by Mango");
        mango.setFont(BODY);
        mango.setFill(Color.ORANGERED);
        titleBox.getChildren().addAll(title, mango);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(40, 0, 0, 0));
        titleBox.setSpacing(10);
        root.setTop(titleBox);
    }

    public void showError(String message) {
        Alert a = new Alert(AlertType.ERROR);
        a.setHeaderText("Game Setup Incorrect");
        a.setContentText(message);
        a.show();
    }

    BorderPane getRoot() {
        return root;
    }
}
