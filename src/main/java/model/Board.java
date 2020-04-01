package main.java.model;

import main.java.model.piece.Piece;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class Board {

    public interface BoardModelEventListener {
        void onDataChanged();
    }

    public static final int ROW = 15;
    public static final int COLUMN = 10;

    private BoardModelEventListener eventListener;

    private Square[][] squares = new Square[ROW][COLUMN];
    private Map<Piece, Square> pieceSquareMap = new HashMap<>();

    private Piece chosenPiece = null;

    public Board(BoardModelEventListener listener) {
        eventListener = listener;
        initSquare();
    }

    //region public Board methods
    public Piece getPiece(int row, int col) {
        return getSquareAt(row, col).getPiece();
    }
    //endregion

    //region private methods
    private void initSquare() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                Square square = new Square();
                squares[row][col] = square;
            }
        }
    }

    private Square getSquareAt(int row, int col) {
        return squares[row][col];
    }
    //endregion

}
