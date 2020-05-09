package main.java.model.board;

import main.java.model.Square;
import main.java.model.move.Move;
import main.java.model.piece.*;
import main.java.model.player.EaglePlayer;
import main.java.model.player.Player;
import main.java.model.player.SharkPlayer;

import java.util.*;

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

        checkSquareEnemyOccupation(piece, destination);

        destination.setPiece(piece);

        pieceSquareMap.put(piece, destination);

        eventListener.onPiecePositionUpdated(move);
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

    //TODO reorganise to private region
    private void attackPiece(Piece attackedPiece, Square attackedSquare) {
        // check if piece is eagle or shark for start pos
        // TODO randomly generate column number
        Random r = new Random();
        int minCol = 0;
        int maxCol = 7;
        int randomCol = r.nextInt(maxCol-minCol) + minCol;

        Square startSquare = getSquareAt(attackedPiece.getStartPos()[0], randomCol);

        startSquare.setPiece(attackedPiece);

        pieceSquareMap.put(attackedPiece, startSquare);
        eventListener.onPieceAttacked(attackedSquare.getRow(), attackedSquare.getCol(), attackedPiece.getStartPos()[0], randomCol);

    }

    //
    private void checkSquareEnemyOccupation(Piece attackingPiece, Square attackedSquare){
        if (attackingPiece instanceof Eagle && attackedSquare.getPiece() != null && !(attackedSquare.getPiece() instanceof Eagle)) {
            attackPiece(attackedSquare.getPiece(), attackedSquare);
        } else if (attackingPiece instanceof Shark && attackedSquare.getPiece() != null && !(attackedSquare.getPiece() instanceof Shark)) {
            attackPiece(attackedSquare.getPiece(), attackedSquare);
        }
    }

    //TODO: refactor redundancy

//    private void checkColourEnemyOccupation(Player attackingPlayer, Square attackedSquare){
//        if (attackingPlayer instanceof EaglePlayer && attackedSquare.getPiece() != null && !(attackedSquare.getPiece() instanceof Eagle)) {
//            System.out.println(attackedSquare.getPiece());
//            attackPiece(attackedSquare.getPiece(), attackedSquare);
//        } else if (attackingPlayer instanceof SharkPlayer && attackedSquare.getPiece() != null && !(attackedSquare.getPiece() instanceof Shark)) {
//            System.out.println("a shark landed on eagle");
//        }
//    }

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
//            checkColourEnemyOccupation(player, square);
            square.setOccupiedPlayer(player);
        }
    }

    public interface BoardModelEventListener {

        void onPiecePositionUpdated(Move move);

        void onPieceAttacked(int attackedRow, int attackedCol, int resetRow, int resetCol);

        void onRocksAdded(Collection<int[]> rockPositionList);
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
