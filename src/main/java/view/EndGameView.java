package main.java.view;

import java.text.DecimalFormat;

import javafx.application.Platform;
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
    private final static String STAGE_NAME = "Game Over";
    private String winner;
    private double sharkScore;
    private double eagleScore;
    private BorderPane root = new BorderPane();
    private static final Font TITLE = Font.font("Impact", 24);
    private static final Font BODY = Font.font("Helvetica", 16);
    private DecimalFormat decimalFormat = new DecimalFormat("#%");


    public EndGameView(String winner, double sharkScore, double eagleScore) {
        this.winner = winner;
        this.sharkScore = sharkScore;
        this.eagleScore = eagleScore;
        initStage();
        draw();
    }

    private void draw() {
        VBox details = new VBox();
        details.setSpacing(20);
        details.setAlignment(Pos.CENTER);

        //winner
        Text winner = new Text("The " + this.winner + " Win!");
        winner.setFont(TITLE);
        winner.setFill(Color.ORANGERED);
        details.getChildren().add(winner);

        if (sharkScore == eagleScore) {
            winner.setText("It was a " + this.winner + "!");
        }

        if (sharkScore > 0.6 || eagleScore > 0.6) {
            Text winConditionText = new Text("The " + this.winner + " captured over 60%");
            winConditionText.setFont(BODY);
            details.getChildren().add(winConditionText);
        }

        //score
        Text sharkPercentage = new Text("Shark Score: " + decimalFormat.format(sharkScore));
        sharkPercentage.setFont(BODY);
        Text eaglePercentage = new Text("Eagle Score: " + decimalFormat.format(eagleScore));
        eaglePercentage.setFont(BODY);

        //play again button
        Button playAgainBt = new Button("Play Again");
        playAgainBt.setFont(BODY);
        playAgainBt.setCursor(Cursor.HAND);
        playAgainBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
        playAgainBt.setPrefWidth(100);

        playAgainBt.setOnAction(event -> {
            SceneManager.getInstance().showStartMenu();
            this.close();
        });

        //quit button
        Button quitBt = new Button("Quit");
        quitBt.setFont(BODY);
        quitBt.setCursor(Cursor.HAND);
        quitBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");
        quitBt.setPrefWidth(100);

        quitBt.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        details.getChildren().addAll(sharkPercentage, eaglePercentage, playAgainBt, quitBt);

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
