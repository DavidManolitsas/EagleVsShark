package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.view.GameInfoView.GameInfoViewEventListener;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-25
 */
public class UndoStage
        extends Stage {

    private final static int WIDTH = 350;
    private final static int HEIGHT = 350;
    private final static String STAGE_NAME = "Undo Move";
    private static final Font BODY = Font.font("Helvetica", 14);
    private BorderPane root = new BorderPane();
    private GameInfoViewEventListener listener;


    public UndoStage(GameInfoViewEventListener listener) {
        this.listener = listener;
        initStage();
        drawStage();
    }

    private void initStage() {
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setTitle(STAGE_NAME);
        this.setScene(new Scene(root));
    }


    private void drawStage() {
        VBox undoRoot = new VBox();
        Text selectUndoText = new Text("Choose a number of turns to undo:\n");
        selectUndoText.setFont(BODY);
        undoRoot.setAlignment(Pos.CENTER);

        Slider undoSlider = new Slider(1, 3, 1);
        undoSlider.setPadding(new Insets(5, 15, 25, 15));
        undoSlider.setShowTickMarks(true);
        undoSlider.setShowTickLabels(true);

        undoSlider.setMajorTickUnit(1);
        undoSlider.setMinorTickCount(1);
        undoSlider.setBlockIncrement(1);
        undoSlider.setOnMouseReleased(event -> {
            undoSlider.setValue(Math.round(undoSlider.getValue()));
        });

        Button undoBt = new Button("Undo Moves");
        undoBt.setWrapText(true);
        undoBt.setFont(BODY);
        undoBt.setStyle("-fx-background-color: ORANGERED; -fx-text-fill: WHITE");

        undoBt.setOnAction(event -> {
            listener.onUndoButtonClicked((int) undoSlider.getValue());
            this.close();
        });

        undoRoot.getChildren().addAll(selectUndoText, undoSlider, undoBt);
        root.setCenter(undoRoot);
    }
}
