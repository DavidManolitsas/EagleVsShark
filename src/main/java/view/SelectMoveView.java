package main.java.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.java.model.Player;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.util.SceneManager;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-05-13
 */
public class SelectMoveView
        extends VBox {

    private GameInfoView gameInfo;
    private ListView<Move> moveList;
    private Text choosePieceText;
    private static final double WIDTH = 250;
    private static final Font HEADING = Font.font("Helvetica", 16);
    private static final Font BODY = Font.font("Helvetica", 14);
    private RadioButton powered;

    public SelectMoveView(GameInfoView gameInfo) {
        this.setPadding(new Insets(10, 0, 0, 0));
        this.setAlignment(Pos.CENTER);
        this.gameInfo = gameInfo;
        initChoosePiece();
        initPoweredUp();
        initMoveList();
    }

    private void initMoveList() {
        moveList = new ListView<>();
        double height = SceneManager.getInstance().getStage().getHeight();
        moveList.setPrefHeight(height - 466);
        moveList.setFixedCellSize(25);
    }


    public void initChoosePiece() {
        choosePieceText = new Text("Choose a piece to move");
        choosePieceText.setFont(HEADING);
        this.getChildren().add(choosePieceText);
    }

    private void initPoweredUp() {
        powered = new RadioButton("Power Move");
        powered.setFont(BODY);
        powered.setPadding(new Insets(5, 0, 5, 0));
        powered.setOnAction(event -> {
            if (powered.isSelected()) {
                moveList.setStyle("-fx-selection-bar: rgba(255,69,0,0.33)");
            } else {
                moveList.setStyle("-fx-selection-bar: rgba(201,202,211,0.33)");
            }
            gameInfo.getGameInfoViewEventListener().onPowerMoveToggled(isPowered());
        });
        this.getChildren().add(powered);
    }

    public void promptChoosePiece() {
        choosePieceText.setText("Choose a piece to move");
        choosePieceText.setFill(Color.BLACK);
        if (!this.getChildren().contains(choosePieceText)) {
            this.getChildren().addAll(choosePieceText, powered);
        }
    }

    public void showChosenPiece(Piece piece) {
        choosePieceText.setText(piece.getPieceType().getName() + " selected");
        if (piece.getTeam().getName().equalsIgnoreCase(Player.EAGLE.getName())) {
            choosePieceText.setFill(Color.ORANGE);
        } else {
            choosePieceText.setFill(Color.valueOf("#3282b8"));
        }
    }

    public void showValidMoveList(List<Move> moves) {
        this.getChildren().clear();
        this.getChildren().addAll(choosePieceText, powered, moveList);

        // update the move list
        ObservableList<Move> moveListObservable = FXCollections.observableArrayList(moves);
        moveList.getItems().removeAll();
        moveList.setItems(moveListObservable);
        moveList.setStyle("-fx-selection-bar: rgba(201,202,211,0.33)");

        // assign name to each Move object
        moveList.setCellFactory(e -> new ListCell<Move>() {

            protected void updateItem(Move item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString().replaceAll("(.)([A-Z])", "$1 $2"));
                    setTextAlignment(TextAlignment.CENTER);
                    setFont(BODY);
                    if (powered.isSelected()) {
                        moveList.setStyle("-fx-selection-bar: rgba(255,69,0,0.33)");
                    }

                }
            }

        });

        moveList.setOnMouseClicked(event -> {
            gameInfo.getGameInfoViewEventListener().onMoveListItemClicked(getSelectedMove());
        });

        drawMoveButton();

    }


    private void drawMoveButton() {
        if (moveList != null) {
            Button moveBt = new Button("Move Piece");
            moveBt.setWrapText(true);
            moveBt.setFont(HEADING);
            moveBt.setStyle("-fx-background-color: #1EB600; -fx-text-fill: WHITE");
            moveBt.setPrefWidth(WIDTH);

            moveBt.setOnAction(event -> {
                Move currentMove = getSelectedMove();
                if (currentMove != null) {
                    currentMove.setPowered(powered.isSelected());
                }
                clearMoveList();
                gameInfo.getGameInfoViewEventListener().onMoveButtonClicked(currentMove);
                // reset the toggle
                powered.setSelected(false);
            });

            this.getChildren().add(moveBt);
        }

    }

    private Move getSelectedMove() throws NullPointerException {
        return moveList.getSelectionModel().getSelectedItem();
    }

    public void clearMoveList() {
        moveList.getItems().clear();
        this.getChildren().clear();
        promptChoosePiece();
    }

    public boolean isPowered() {
        return powered.isSelected();
    }

    public void setIsPowered(Boolean isPowered) {
        powered.setSelected(isPowered);
    }

    public void turnOffPowered() {
        powered.setSelected(false);
        powered.setDisable(true);
    }

    public void turnOnPowered() {
        powered.setDisable(false);
    }
}
