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
import javafx.scene.layout.HBox;
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

        HBox eagleVsShark = new HBox();
        eagleVsShark.setSpacing(10);
        eagleVsShark.setAlignment(Pos.CENTER);
        Text eagleText = new Text("Eagle");
        eagleText.setFont(TITLE);
        eagleText.setFill(Color.ORANGE);
        Text vsText = new Text("vs.");
        vsText.setFont(TITLE);
        vsText.setFill(Color.BLACK);
        Text sharkText = new Text("Shark");
        sharkText.setFont(TITLE);
        sharkText.setFill(Color.valueOf("#3282b8"));
        eagleVsShark.getChildren().addAll(eagleText, vsText, sharkText);


        Text mango = new Text("Created by Mango");
        mango.setFont(BODY);
        titleBox.getChildren().addAll(eagleVsShark, mango);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(30, 0, 0, 0));
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
