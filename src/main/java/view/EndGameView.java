package main.java.view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.util.SceneManager;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-04-16
 */
public class EndGameView
        extends Stage {

    private final static int WIDTH = 300;
    private final static int HEIGHT = 300;
    private final static String STAGE_NAME = "Winner!";
    private String winner;
    BorderPane root = new BorderPane();
    private static final Font TITLE = Font.font("Impact", 24);
    private static final Font BODY = Font.font("Helvetica", 16);


    public EndGameView(String winner) {
        this.winner = winner;
        initStage();
        draw();
    }

    private void draw() {
        VBox details = new VBox();
        details.setSpacing(20);
        details.setAlignment(Pos.CENTER);

        Text winner = new Text(this.winner);
        winner.setFont(TITLE);
        winner.setFill(Color.ORANGERED);
        Button playAgainBt = new Button("Play Again");
        playAgainBt.setFont(BODY);
        playAgainBt.setCursor(Cursor.HAND);
        playAgainBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");


        details.getChildren().addAll(winner, playAgainBt);

        playAgainBt.setOnAction(event -> {
            SceneManager.getInstance().showStartMenu();
            this.close();
        });

        root.setCenter(details);
    }

    private void initStage() {
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setTitle(STAGE_NAME);
        this.setScene(new Scene(root));

        this.setOnHidden(event -> {
            SceneManager.getInstance().showStartMenu();
            this.close();
        });
    }

}