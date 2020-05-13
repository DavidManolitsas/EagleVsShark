package main.java.view;

import java.text.DecimalFormat;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-13
 */
public class Scoreboard
        extends VBox {

    private int turnCount;
    private int totalTurns;
    private double sharkScore;
    private double eagleScore;
    private static final Font BODY = Font.font("Helvetica", 14);
    private DecimalFormat decimalFormat = new DecimalFormat("#%");

    public Scoreboard(int turnCount, int totalTurns, double sharkScore, double eagleScore) {
        this.turnCount = turnCount;
        this.totalTurns = totalTurns;
        this.sharkScore = sharkScore;
        this.eagleScore = eagleScore;
        initScoreboard();
    }

    private void initScoreboard() {
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        drawTurnCount();
        drawScores();
    }

    private void drawTurnCount() {
        Text turnCountText = new Text("Turn No. " + turnCount + "/" + totalTurns);
        turnCountText.setFont(BODY);
        this.getChildren().add(turnCountText);
    }

    private void drawScores() {
        Text sharkScoreText = new Text("Shark Score: " + decimalFormat.format(sharkScore));
        sharkScoreText.setFont(BODY);
        Text eagleScoreText = new Text("Eagle Score: " + decimalFormat.format(eagleScore));
        eagleScoreText.setFont(BODY);
        this.getChildren().addAll(sharkScoreText, eagleScoreText);
    }

    public void updateScoreboard(int turnCount, double sharkScore, double eagleScore) {
        this.turnCount = turnCount;
        this.sharkScore = sharkScore;
        this.eagleScore = eagleScore;
        drawTurnCount();
        drawScores();
    }

}
