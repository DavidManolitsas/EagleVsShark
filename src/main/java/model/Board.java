package main.java.model;

import main.java.model.move.Move;
import main.java.model.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public int getSharkSquareCount() {
        int count = 0;
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (getSquareAt(row, col).getOccupiedPlayer() instanceof SharkPlayer) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getEagleSquareCount() {
        int count = 0;
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (getSquareAt(row, col).getOccupiedPlayer() instanceof EaglePlayer) {
                    count++;
                }
            }
        }
        return count;
    }

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
        int[] startPos = move.getRoute().get(0);
        int[] destinationPos = move.getFinalPosition();

        Square start = getSquareAt(startPos[0], startPos[1]);
        start.setPiece(null);

        Square destination = getSquareAt(destinationPos[0], destinationPos[1]);
        destination.setPiece(piece);
        pieceSquareMap.put(piece, destination);

        eventListener.onPiecePositionUpdated(move);
    }

    public void updateTerritory(Move move, Player player) {
        for (int[] position : move.getPaintInfo()) {
            Square square = getSquareAt(position[0], position[1]);
            square.setOccupiedPlayer(player);
        }
    }

    public List<int[]> getSharksPositions() {
        List<int[]> list = new ArrayList<>();

        for (Piece piece : pieceSquareMap.keySet()) {
            if (piece instanceof Shark) {
                Square square = pieceSquareMap.get(piece);
                int row = square.getRow();
                int col = square.getCol();
                list.add(new int[] {row, col});
            }
        }
        return list;
    }

    public List<Move> validatePossibleMoves(List<Move> moves) {
        List<Move> validatedMoves = new ArrayList<>();
        for (Move move : moves) {
            int[] finalPos = move.getFinalPosition();

            if (isSquareValid(finalPos)) {
                removeInvalidPaintSquare(move);
                validatedMoves.add(move);
            }
        }
        return validatedMoves;
    }
    //endregion

    //region private methods
    private void initSquare() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                Square square = new Square(row, col);
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

    /**
     * @param position
     *         Destination position
     *
     * @return true when:
     * 1. destination square is on the board
     * 2. destination square has no piece which belongs to the same player
     */
    private boolean isSquareValid(int[] position) {
        if (isPositionOutOfBound(position)) {
            return false;
        }

        // Check if the square already has piece on it
        Piece piece = getSquareAt(position[0], position[1]).getPiece();
        return piece == null || !piece.getClass().getSuperclass().equals(chosenPiece.getClass().getSuperclass());
    }

    private boolean isPositionOutOfBound(int[] position) {
        return position[0] < 0 || position[0] >= ROW ||
                position[1] < 0 || position[1] >= COLUMN;
    }

    private void removeInvalidPaintSquare(Move move) {
        List<int[]> paintInfo = move.getPaintInfo();
        paintInfo.removeIf(this::isPositionOutOfBound);
    }
    //endregion

}
