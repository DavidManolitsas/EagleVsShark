package main.java.model.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.model.Square;
import main.java.model.move.Move;
import main.java.model.piece.BaldEagle;
import main.java.model.piece.GoblinShark;
import main.java.model.piece.GoldenEagle;
import main.java.model.piece.Hammerhead;
import main.java.model.piece.HarpyEagle;
import main.java.model.piece.Piece;
import main.java.model.piece.SawShark;
import main.java.model.piece.Shark;
import main.java.model.player.EaglePlayer;
import main.java.model.player.Player;
import main.java.model.player.SharkPlayer;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 * <p>
 * Invariant:
 * 1. square != null
 * 2. pieceSquareMap != null && pieceSquareMap.size == 6
 */
public class BoardImpl
        implements Board {

    public interface BoardModelEventListener {

        void onPiecePositionUpdated(Move move);

        void onRocksAdded(Collection<int[]> rockPositionList);

        void initBoardView(int rows, int cols, Square[] topRow, Square[] bottomRow);
    }

    private BoardModelEventListener eventListener;

    private int totalRows;
    private int totalCols;

    private Square[][] squares;
    private Map<Piece, Square> pieceSquareMap;

    private Piece chosenPiece;

    /**
     * Requires:
     * listener != null
     */
    public BoardImpl(BoardModelEventListener listener) {
        eventListener = listener;
    }

    // region public Board methods
    @Override
    public void initBoard(int rows, int cols, int sharks, int eagles) {
        totalRows = rows;
        totalCols = cols;
        chosenPiece = null;

        initSquare();
        initPieces(sharks, eagles);
    }

    @Override
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

    @Override
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

    /**
     * Requires:
     * 1. row >= 0 && col >= 0
     * 2. row < ROW && col < COL
     */
    @Override
    public Piece getPiece(int row, int col) {
        return getSquareAt(row, col).getPiece();
    }

    @Override
    public void setChosenPiece(Piece chosenPiece) {
        this.chosenPiece = chosenPiece;
    }

    @Override
    public Piece getChosenPiece() {
        return chosenPiece;
    }

    /**
     * Requires:
     * 1. move != null
     * 2. piece != null
     * <p>
     * Ensures:
     * 1. pieceSquareMap.get(piece) == destination
     * 2. start.getPiece == null
     */
    @Override
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

    /**
     * Requires:
     * 1. move != null
     * 2. player != null
     * <p>
     * Ensures:
     * 1. squares of PaintInfo are occupied by the player
     */
    @Override
    public void updateTerritory(Move move, Player player) {
        for (int[] position : move.getPaintShape().getPaintInfo()) {
            Square square = getSquareAt(position[0], position[1]);
            square.setOccupiedPlayer(player);
        }
    }

    @Override
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

    /**
     * Requires:
     * 1. moves != null
     */
    @Override
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

    /**
     * Requires:
     * 1. position != null
     *
     * @param position
     *         Destination position
     *
     * @return true when:
     * 1. destination square is on the board
     * 2. destination square has no piece which belongs to the same player
     */
    public boolean isSquareValid(int[] position) {
        if (isPositionOutOfBound(position)) {
            return false;
        }

        // Check if the square already has piece on it
        Piece piece = getSquareAt(position[0], position[1]).getPiece();
        return piece == null ||
                (chosenPiece != null &&
                        !piece.getClass().getSuperclass().equals(chosenPiece.getClass().getSuperclass()));
    }

    @Override
    public int getTotalRows() {
        return totalRows;
    }

    @Override
    public int getTotalCols() {
        return totalCols;
    }

    public BoardModelEventListener getEventListener() {
        return eventListener;
    }
    // endregion

    // region private methods
    private void initSquare() {
        squares = new Square[totalRows][totalCols];

        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                Square square = new Square(row, col);
                squares[row][col] = square;
            }
        }
    }

    private void initPieces(int sharks, int eagles) {
        pieceSquareMap = new HashMap<>();

        int topRow = 0;
        int bottomRow = totalRows - 1;
        // Gaps between each piece
        int sharkSplit = totalCols / sharks;
        int sharkPosCount = sharkSplit;
        int eagleSplit = totalCols / eagles;
        int eaglePosCount = eagleSplit;


        // Get each number of sharks
        int goblinSharks, sawSharks, hammerHeads, goldenEagles, baldEagles, harpyEagles;
        ;
        if (sharks % 3 == 0) {
            goblinSharks = sharks / 3;
            sawSharks = sharks / 3;
            hammerHeads = sharks / 3;
        } else if (sharks % 3 == 1) {
            goblinSharks = sharks / 3;
            sawSharks = sharks / 3;
            hammerHeads = sharks / 3 + 1;
        } else {
            goblinSharks = sharks / 3;
            sawSharks = sharks / 3 + 1;
            hammerHeads = sharks / 3 + 1;
        }

        //  Get each number of eagles
        if (eagles % 3 == 0) {
            goldenEagles = eagles / 3;
            baldEagles = eagles / 3;
            harpyEagles = eagles / 3;
        } else if (eagles % 3 == 1) {
            goldenEagles = eagles / 3;
            baldEagles = eagles / 3;
            harpyEagles = eagles / 3 + 1;
        } else {
            goldenEagles = eagles / 3;
            baldEagles = eagles / 3 + 1;
            harpyEagles = eagles / 3 + 1;
        }

        ArrayList<Piece> pieces = new ArrayList<Piece>();
        // Initialise Sharks
        for (int i = 0; i < goblinSharks; i++) {
            pieces.add(new GoblinShark(bottomRow, sharkSplit - 1));
            sharkSplit += sharkPosCount;
        }

        for (int i = 0; i < hammerHeads; i++) {
            pieces.add(new Hammerhead(bottomRow, sharkSplit - 1));
            sharkSplit += sharkPosCount;
        }

        for (int i = 0; i < sawSharks; i++) {
            pieces.add(new SawShark(bottomRow, sharkSplit - 1));
            sharkSplit += sharkPosCount;
        }

        // Initialise Eagles
        for (int i = 0; i < baldEagles; i++) {
            pieces.add(new BaldEagle(topRow, eagleSplit - 1));
            eagleSplit += eaglePosCount;
        }

        for (int i = 0; i < goldenEagles; i++) {
            pieces.add(new GoldenEagle(topRow, eagleSplit - 1));
            eagleSplit += eaglePosCount;
        }

        for (int i = 0; i < harpyEagles; i++) {
            pieces.add(new HarpyEagle(topRow, eagleSplit - 1));
            eagleSplit += eaglePosCount;
        }

        for (Piece piece : pieces) {
            int[] pos = piece.getStartPos();
            Square square = getSquareAt(pos[0], pos[1]);
            square.setPiece(piece);
            pieceSquareMap.put(piece, square);
        }
    }

    public Square[] getTopRow() {
        return squares[0];

    }

    public Square[] getBottomRow() {
        return squares[totalRows - 1];
    }


    /**
     * Requires:
     * 1. row >= 0 && col >= 0
     * 2. row < ROW && col < COL
     */
    private Square getSquareAt(int row, int col) {
        return squares[row][col];
    }

    /**
     * Requires:
     * 1. position != null
     */
    private boolean isPositionOutOfBound(int[] position) {
        return position[0] < 0 || position[0] >= totalRows ||
                position[1] < 0 || position[1] >= totalCols;
    }

    /**
     * Requires:
     * 1. move != null
     */
    private void removeInvalidPaintSquare(Move move) {
        List<int[]> paintInfo = move.getPaintShape().getPaintInfo();
        paintInfo.removeIf(this::isPositionOutOfBound);
    }
    // endregion


}
