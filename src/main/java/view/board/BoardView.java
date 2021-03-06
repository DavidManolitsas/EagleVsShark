package main.java.view.board;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import main.java.model.Player;
import main.java.model.Square;
import main.java.model.commands.AttackPieceInfo;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

public class BoardView
        extends GridPane
        implements BoardViewI {

    public interface BoardViewEventListener {
        void onSquareClicked(int row, int col);
    }

    public static final String VIEW_ID_PIECE = "piece";
    public static final String VIEW_ID_ROCKS = "rocks";
    public static final String VIEW_ID_PREVIEW = "preview";

    public static final String COLOUR_EAGLE = "ORANGE";
    public static final String COLOUR_SHARK = "#3282b8";
    public static final String COLOUR_NEUTRAL = "#f1f3f4";
    public static final String COLOUR_ROUTE_PREVIEW = "rgba(237, 124, 124, 0.37)";
    public static final String COLOUR_PAINT_AREA_PREVIEW = "rgba(193, 193, 193, 0.6)";

    public static final int SQUARE_SIZE = 40;
    public static final int PIECE_SIZE = 38;
    public static final int OBSTACLES_SIZE = 28;

    private int totalRows;
    private int totalCols;

    private Move lastPreviewMove = null;

    private BoardViewEventListener boardViewEventListener;

    public BoardView() {
        super();
    }

    //region public BoardView methods
    @Override
    public void showMovePreview(Move move) {
        if (lastPreviewMove != null) {
            removeMovePreview();
        }

        for (int[] position : move.getPaintShape().getPaintInfo()) {
            paintSquare(position, COLOUR_PAINT_AREA_PREVIEW);
        }

        for (int[] position : move.getRoute()) {
            paintSquare(position, COLOUR_ROUTE_PREVIEW);
        }
        lastPreviewMove = move;
    }

    @Override
    public void removeMovePreview() {
        if (lastPreviewMove == null) {
            return;
        }

        // Remove duplicated positions
        Set<int[]> paintInfo = new LinkedHashSet<>(lastPreviewMove.getPaintShape().getPaintInfo());
        paintInfo.addAll(lastPreviewMove.getRoute());
        List<int[]> allPositions = new ArrayList<>(paintInfo);

        for (int[] position : allPositions) {
            StackPane square = getSquareAt(position[0], position[1]);
            if (square != null) {
                square.getChildren().removeIf(child -> child.getId().equals(VIEW_ID_PREVIEW));
            }
        }
        lastPreviewMove = null;
    }

    private void updateTerritory(Move move, Player currentPlayer) {
        String color;

        if (currentPlayer == Player.EAGLE) {
            color = COLOUR_EAGLE;
        } else {
            color = COLOUR_SHARK;
        }

        for (int[] position : move.getPaintShape().getPaintInfo()) {
            StackPane square = getSquareAt(position[0], position[1]);
            if (square != null) {
                square.setStyle("-fx-border-color: black; -fx-background-color: " + color + ";");
            }
        }
    }

    private void removeTerritory(Move move, List<Player> occupiedHistory) {
        int i = 0;
        for (int[] position : move.getPaintShape().getPaintInfo()) {
            StackPane square = getSquareAt(position[0], position[1]);
            String color = COLOUR_NEUTRAL;

            Player player = occupiedHistory.get(i++);
            if (player == Player.EAGLE) {
                color = COLOUR_EAGLE;
            } else if (player == Player.SHARK) {
                color = COLOUR_SHARK;
            }

            if (square != null) {
                square.setStyle("-fx-border-color: black; -fx-background-color: " + color + ";");
            }
        }
    }
    //endregion

    //region BoardModelEvent methods
    @Override
    public void onBoardInitialised(int rows, int cols, Set<Piece> pieces,
                                   List<Square> obstacleList) {

        totalRows = rows;
        totalCols = cols;

        lastPreviewMove = null;

        drawBoard();
        drawPieces(pieces);
        drawObstacles(obstacleList);
    }

    @Override
    public void updatePiecePosition(int originalRow, int originalCol, int destinationRow, int destinationCol) {
        StackPane start = getSquareAt(originalRow, originalCol);
        Node piece = start.getChildren().filtered(child -> child.getId().equals(VIEW_ID_PIECE)).get(0);
        start.getChildren().remove(piece);

        StackPane destination = getSquareAt(destinationRow, destinationCol);
        destination.getChildren().add(piece);
    }

    @Override
    public void onPieceSelected(int row, int col) {
        removeMovePreview();
    }

    @Override
    public void onPieceMoved(Move move, Player currentPlayer) {
        removeMovePreview();

        int[] startPos = move.getRoute().get(0);
        int[] destinationPos = move.getFinalPosition();
        updatePiecePosition(startPos[0], startPos[1], destinationPos[0], destinationPos[1]);
        updateTerritory(move, currentPlayer);
    }

    @Override
    public void onTimeRanOut() {
        removeMovePreview();
    }

    @Override
    public void onUndoMove(Move move, List<Player> occupiedHistory, AttackPieceInfo attackPieceInfo) {
        removeMovePreview();

        int[] startPos = move.getReverseRoute().get(0);
        int[] destinationPos = move.getReverseRoute().get(1);
        updatePiecePosition(startPos[0], startPos[1], destinationPos[0], destinationPos[1]);
        if (!attackPieceInfo.getAttackedPieces().isEmpty()) {
            undoAttackedPiecePosition(attackPieceInfo);
        }

        removeTerritory(move, occupiedHistory);
    }
    //endregion

    //region private methods
    private void drawBoard() {
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                StackPane square = getSquare(row, col);
                add(square, col, row);
            }
        }

        for (int i = 0; i < totalCols; i++) {
            getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE,
                                                             Control.USE_COMPUTED_SIZE,
                                                             Double.POSITIVE_INFINITY,
                                                             Priority.ALWAYS,
                                                             HPos.CENTER,
                                                             true));
        }


        for (int i = 0; i < totalRows; i++) {
            getRowConstraints().add(new RowConstraints(SQUARE_SIZE,
                                                       Control.USE_COMPUTED_SIZE,
                                                       SQUARE_SIZE,
                                                       Priority.ALWAYS,
                                                       VPos.CENTER,
                                                       true));
        }
    }

    private void drawPieces(Set<Piece> pieces) {
        for (Piece piece : pieces) {
            addPiece(piece.getStartPos()[0], piece.getStartPos()[1], piece.getImgPath());
        }
    }

    private void drawObstacles(List<Square> obstacleList) {
        for (Square square : obstacleList) {
            addObstacles(square.getRow(), square.getCol(), square.getObstacle().getImgPath());
        }
    }

    private StackPane getSquare(int row, int col) {
        StackPane square = new StackPane();
        square.setStyle("-fx-border-color: black; -fx-background-color: " + COLOUR_NEUTRAL + ";");
        square.setOnMouseClicked(event -> getBoardViewEventListener().onSquareClicked(row, col));
        return square;
    }

    public StackPane getSquareAt(int row, int col) {
        for (Node child : getChildren()) {
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                return (StackPane) child;
            }
        }
        return null;
    }

    private void addPiece(int row, int col, String pieceImgPath) {
        StackPane square = getSquareAt(row, col);

        if (square == null) {
            return;
        }

        Image image = new Image(pieceImgPath, PIECE_SIZE, PIECE_SIZE, true, false);
        ImageView imageView = new ImageView(image);
        imageView.setId(VIEW_ID_PIECE);
        square.getChildren().add(imageView);
    }

    private void paintSquare(int[] position, String color) {
        StackPane square = getSquareAt(position[0], position[1]);
        if (square != null) {
            Node route = new StackPane();
            route.setStyle("-fx-background-color: " + color + ";");
            route.setId(VIEW_ID_PREVIEW);
            square.getChildren().add(route);
        }
    }

    private void addObstacles(int row, int col, String imgPath) {
        StackPane square = getSquareAt(row, col);

        if (square == null) {
            return;
        }

        Image image = new Image(imgPath, OBSTACLES_SIZE, OBSTACLES_SIZE, true, false);
        ImageView imageView = new ImageView(image);
        imageView.setId(VIEW_ID_ROCKS);
        square.getChildren().add(imageView);
    }

    private void undoAttackedPiecePosition(AttackPieceInfo attackPieceInfo) {
        for (int i = 0; i < attackPieceInfo.getAttackedPieces().size(); ++i) {
            updatePiecePosition(attackPieceInfo.getNewPositions().get(i)[0],
                                attackPieceInfo.getNewPositions().get(i)[1],
                                attackPieceInfo.getPreviousPositions().get(i)[0],
                                attackPieceInfo.getPreviousPositions().get(i)[1]);
        }
    }
    //endregion

    public BoardViewEventListener getBoardViewEventListener() {
        return Objects.requireNonNull(boardViewEventListener);
    }

    @Override
    public void setBoardViewEventListener(BoardViewEventListener boardViewEventListener) {
        if (boardViewEventListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.boardViewEventListener = boardViewEventListener;
    }
}
