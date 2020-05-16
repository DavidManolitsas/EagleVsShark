package main.java.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import main.java.ResPath;
import main.java.model.Square;
import main.java.model.board.BoardImpl.BoardModelEventListener;
import main.java.model.move.Move;
import main.java.model.piece.*;

import java.util.*;

public class BoardView
        extends GridPane
        implements BoardModelEventListener {

    public interface BoardViewEventListener {
        void onSquareClicked(int row, int col);
    }

    public static final String VIEW_ID_PIECE = "piece";
    public static final String VIEW_ID_ROCKS = "rocks";
    public static final String VIEW_ID_PREVIEW = "preview";
    public static final String VIEW_ID_HIGHLIGHT = "highlight";

    public static final String COLOUR_EAGLE = "#ffebd9";
    public static final String COLOUR_SHARK = "#3282b8";
    public static final String COLOUR_NEUTRAL = "#f1f3f4";
    public static final String COLOUR_ROUTE_PREVIEW = "rgba(237, 124, 124, 0.37)";
    public static final String COLOUR_PAINT_AREA_PREVIEW = "rgba(193, 193, 193, 0.6)";

    public static final int SQUARE_SIZE = 40;
    public static final int PIECE_SIZE = 38;
    public static final int ROCKS_SIZE = 38;

    private int totalRows;
    private int totalCols;

    private int[] lastHighlightPos = null;
    private Move lastPreviewMove = null;

    private BoardViewEventListener boardViewEventListener;

    public BoardView() {
        super();
    }

    //region public BoardView methods
    public void highlightSquare(int row, int col) {
        if (lastHighlightPos != null) {
            removeHighlight();
        }

        StackPane square = getSquareAt(row, col);
        Node highlight = new StackPane();
        highlight.setId(VIEW_ID_HIGHLIGHT);
        highlight.setStyle("-fx-background-color: " + COLOUR_ROUTE_PREVIEW + ";");
        square.getChildren().add(highlight);

        lastHighlightPos = new int[] {row, col};

        removeMovePreview();
    }

    public void removeHighlight() {
        if (lastHighlightPos == null) {
            return;
        }

        StackPane square = getSquareAt(lastHighlightPos[0], lastHighlightPos[1]);
        if (square != null) {
            square.getChildren().removeIf(child -> child.getId().equals(VIEW_ID_HIGHLIGHT));
        }
        lastHighlightPos = null;
    }

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

    public void updateTerritory(Move move, int turnCount) {
        String color;

        if (turnCount % 2 == 0) {
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
    //endregion

    //region BoardModelEvent methods
    @Override
    public void onBoardInitialised(int rows, int cols, Square[] topRow, Square[] bottomRow) {
        totalRows = rows;
        totalCols = cols;

        lastHighlightPos = null;
        lastPreviewMove = null;

        drawBoard();
        drawPieces(topRow, bottomRow);
    }

    @Override
//    public void onPieceAttacked(int attackedRow, int attackedCol, int resetRow, int resetCol){
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
        highlightSquare(row, col);
    }

    @Override
    public void onPieceMoved(Move move, int turnCount) {
        removeMovePreview();
        removeHighlight();

        int[] startPos = move.getRoute().get(0);
        int[] destinationPos = move.getFinalPosition();
        updatePiecePosition(startPos[0], startPos[1], destinationPos[0], destinationPos[1]);
        updateTerritory(move, turnCount);
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

    private void drawPieces(Square[] topRow, Square[] bottomRow) {
        String baldEagleImgPath = ResPath.PIECE_BALD_EAGLE;
        String goldenEagleImgPath = ResPath.PIECE_GOLDEN_EAGLE;
        String harpyEagleImgPath = ResPath.PIECE_HARPY_EAGLE;
        String goblinSharkImgPath = ResPath.PIECE_GOBLIN_SHARK;
        String hammerheadImgPath = ResPath.PIECE_HAMMERHEAD;
        String sawSharkImgPath = ResPath.PIECE_SAW_SHARK;


        for (Square square : topRow) {
            Piece piece = square.getPiece();

            if (piece instanceof BaldEagle) {
                addPiece(square.getRow(), square.getCol(), baldEagleImgPath);
            } else if (piece instanceof HarpyEagle) {
                addPiece(square.getRow(), square.getCol(), harpyEagleImgPath);
            } else if (piece instanceof GoldenEagle) {
                addPiece(square.getRow(), square.getCol(), goldenEagleImgPath);
            }
        }

        for (Square square : bottomRow) {
            Piece piece = square.getPiece();

            if (piece instanceof Hammerhead) {
                addPiece(square.getRow(), square.getCol(), hammerheadImgPath);
            } else if (piece instanceof GoblinShark) {
                addPiece(square.getRow(), square.getCol(), goblinSharkImgPath);
            } else if (piece instanceof SawShark) {
                addPiece(square.getRow(), square.getCol(), sawSharkImgPath);
            }
        }
    }

    private StackPane getSquare(int row, int col) {
        StackPane square = new StackPane();
        square.setStyle("-fx-border-color: black; -fx-background-color: " + COLOUR_NEUTRAL + ";");
        square.setOnMouseClicked(event -> getBoardViewEventListener().onSquareClicked(row, col));
        return square;
    }

    private StackPane getSquareAt(int row, int col) {
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

    private void addRocks(int row, int col, String rockImgPath) {
        StackPane square = getSquareAt(row, col);

        if (square == null) {
            return;
        }

        Image image = new Image(rockImgPath, ROCKS_SIZE, ROCKS_SIZE, true, false);
        ImageView imageView = new ImageView(image);
        imageView.setId(VIEW_ID_ROCKS);
        square.getChildren().add(imageView);
    }
    //endregion

    public BoardViewEventListener getBoardViewEventListener() {
        return Objects.requireNonNull(boardViewEventListener);
    }

    public void setBoardViewEventListener(BoardViewEventListener boardViewEventListener) {
        if (boardViewEventListener == null) {
            throw new NullPointerException("Must provide a non-null listener.");
        }

        this.boardViewEventListener = boardViewEventListener;
    }
}
