package main.java.model;

import main.java.model.move.Move;
import main.java.model.piece.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class Board {

    public interface BoardModelEventListener {
        void onPiecePositionUpdated(Move move);
    }

    public static final int ROW = 15;
    public static final int COLUMN = 10;

    private BoardModelEventListener eventListener;

    private Square[][] squares = new Square[ROW][COLUMN];
    private Map<Piece, Square> pieceSquareMap = new HashMap<>();

    private Piece chosenPiece = null;

    private Move previewMove = null;

    public Board(BoardModelEventListener listener) {
        eventListener = listener;
        initSquare();
    }

    //region public Board methods
    public Piece getPiece(int row, int col) {
        return getSquareAt(row, col).getPiece();
    }

    public void setChosenPiece(Piece chosenPiece) {
        this.chosenPiece = chosenPiece;
    }

    public Move getPreviewMove() {
        return previewMove;
    }

    public void setPreviewMove(Move previewMove) {
        this.previewMove = previewMove;
    }

    public void updatePiecePosition(Move move) {
        Integer[] startPos = move.getRoute().get(0);
        int[] destinationPos = move.getFinalPosition();

        Square start = getSquareAt(startPos[0], startPos[1]);
        start.setPiece(null);

        Square destination = getSquareAt(destinationPos[0], destinationPos[1]);
        destination.setPiece(chosenPiece);

        eventListener.onPiecePositionUpdated(move);
    }

    //endregion

    //region private methods
    private void initSquare() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                Square square = new Square();

                // TODO: Refactor test code
                Piece piece = null;
                if (row == 0) {
                    if (col == 4) {
                        piece = new GoblinShark();
                    } else if (col == 5) {
                        piece = new Hammerhead();
                    } else if (col == 6) {
                        piece = new SawShark();
                    }
                } else if (row == 14) {
                    if (col == 4) {
                        piece = new BaldEagle();
                    } else if (col == 5) {
                        piece = new GoldenEagle();
                    } else if (col == 6) {
                        piece = new HarpyEagle();
                    }
                }

                if (piece != null) {
                    square.setPiece(piece);
                }
                squares[row][col] = square;
            }
        }
    }

    private Square getSquareAt(int row, int col) {
        return squares[row][col];
    }
    //endregion

}
