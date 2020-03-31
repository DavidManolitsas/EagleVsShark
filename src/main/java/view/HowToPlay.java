package main.java.view;


import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-31
 */
public class HowToPlay extends Stage {

    private final static int WIDTH = 500;
    private final static int HEIGHT = 700;
    private final static String STAGE_NAME = "How to play: Eagle vs. Shark";

    public HowToPlay(){
        draw();
    }

    private void draw(){
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setTitle(STAGE_NAME);

        BorderPane root = new BorderPane();

        //Text area
        String howTo = "EAGLE VS. SHARK\n\nHow to play:\n\nCapture more territory than the other team before the number of turns equals 20\n\nblah blah blah blah";
        TextArea textArea = new TextArea(howTo);
        textArea.setFont(Font.font("Helvetica", 14));
        textArea.setPrefWidth(WIDTH - 12);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        //Scroll pane
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        //VBox
        VBox vbox = new VBox(scrollPane);
        root.setCenter(vbox);

        this.setScene(new Scene(root));

    }
}
