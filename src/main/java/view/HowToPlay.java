package main.java.view;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-03-31
 */
public class HowToPlay
        extends Stage {


    private final static int WIDTH = 500;
    private final static int HEIGHT = 700;
    private final static String STAGE_NAME = "How to play: Eagle vs. Shark";
    private String howTo = "";
    BorderPane root = new BorderPane();

    public HowToPlay() throws FileNotFoundException {
        drawRules();
        initStage();
    }

    private void drawRules() throws FileNotFoundException {
        //set the rules from .txt
        setHowTo();
        //Add the rules to scroll pane
        VBox box = new VBox();
        Text text = new Text(howTo);
        text.setFont(Font.font("Helvetica", 14));
        box.getChildren().add(text);
        ScrollPane scrollPane = new ScrollPane(box);
        scrollPane.setPadding(new Insets(20, 20, 20, 20));

        root.setCenter(scrollPane);
    }


    private void setHowTo() throws FileNotFoundException {
        File howToPlayFile = new File("howToPlay.txt");

        Scanner input = new Scanner(howToPlayFile);

        while (input.hasNextLine()) {
            howTo += input.nextLine() + "\n";
        }
        input.close();
    }

    private void initStage() {
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setTitle(STAGE_NAME);
        this.setScene(new Scene(root));
    }


}
