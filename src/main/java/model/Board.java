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

    public Board(BoardModelEventListener listener) {
        eventListener = listener;
        initSquare();
        initPieces();
    }

    //region public Board methods
    public Piece getPiece(int row, int col) {
        return getSquareAt(row, col).getPiece();
    }

    public void setChosenPiece(Piece chosenPiece) {
        this.chosenPiece = chosenPiece;
    }

    public Piece getChosenPiece() {
        return chosenPiece;
    }

    public void updatePiecePosition(Move move, Piece piece) {
        Integer[] startPos = move.getRoute().get(0);
        int[] destinationPos = move.getFinalPosition();

        Square start = getSquareAt(startPos[0], startPos[1]);
        start.setPiece(null);

        Square destination = getSquareAt(destinationPos[0], destinationPos[1]);
        destination.setPiece(piece);

        eventListener.onPiecePositionUpdated(move);
    }

    public void updateTerritory(Move move, Player player) {
        for (Integer[] position : move.getPaintInfo()) {
            Square square = getSquareAt(position[0], position[1]);
            square.setOccupiedPlayer(player);
        }
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

    private void initPieces() {
        int topRow = 0;
        int bottomRow = ROW - 1;
        int[] pieceCols = {4, 5, 6};

        // Adding pieces by order
        Piece[] pieces = {
                new BaldEagle(topRow, pieceCols[0]),
                new GoldenEagle(topRow, pieceCols[1]),
                new HarpyEagle(topRow, pieceCols[2]),
                new GoblinShark(bottomRow, pieceCols[0]),
                new Hammerhead(bottomRow, pieceCols[1]),
                new SawShark(bottomRow, pieceCols[2]),
        };

        for (Piece piece : pieces) {
            int[] pos = piece.getStartPos();
            Square square = getSquareAt(pos[0], pos[1]);
            square.setPiece(piece);
            pieceSquareMap.put(piece, square);
        }
    }

    private Square getSquareAt(int row, int col) {
        return squares[row][col];
    }
    //endregion

}
