package main.java.model.board;

import main.java.model.Player;
import main.java.model.Square;
import main.java.model.memento.CareTaker;
import main.java.model.memento.Memento;
import main.java.model.memento.Originator;
import main.java.model.move.CustomPieceMove;
import main.java.model.move.Move;
import main.java.model.obstacles.ObstacleType;
import main.java.model.piece.Piece;
import main.java.model.piece.PieceType;
import main.java.util.BoardHelper;

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

    private BoardModelEventListener eventListener;

    private int totalRows;
    private int totalCols;
    private int sharkNums;
    private int eagleNums;
    private Square[][] squares;
    private Map<Piece, Square> pieceSquareMap;
    private Piece chosenPiece;

    private boolean isEagleUndo;
    private boolean isSharkUndo;
    private Originator originator;
    private CareTaker careTaker;

    private List<Square> obstacleList;

    public BoardImpl(BoardModelEventListener boardListener, int rows, int cols,
                     int sharks, int eagles) {
        this.eventListener = boardListener;
        totalRows = rows;
        totalCols = cols;
        sharkNums = sharks;
        eagleNums = eagles;
        chosenPiece = null;
        isEagleUndo = false;
        isSharkUndo = false;
        originator = new Originator();
        careTaker = new CareTaker();

        initBoard();
    }

    // region public Board methods
    @Override
    public int getSharkSquareCount() {
        int count = 0;
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (getSquareAt(row, col).getOccupiedPlayer() == Player.SHARK) {
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
                if (getSquareAt(row, col).getOccupiedPlayer() == Player.EAGLE) {
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
    public Piece getChosenPiece() {
        return chosenPiece;
    }

    /**
     * Requires:
     * 1. steps >= 1 && steps <= 3
     *
     * @param steps
     * @param player
     *
     * @return if the undo is valid
     */
    @Override
    public boolean retrieveSteps(int steps, Player player) {
        // All conditions checking can be moved to somewhere else in the future
        // like controllers
        if (careTaker.getMementoNumbers() < 2 * steps) {
            // The require steps is exceed the maximum history record
            return false;
        }

        if (player == Player.EAGLE && !isEagleUndo) {
            isEagleUndo = true;
        } else if (player == Player.SHARK && !isSharkUndo) {
            isSharkUndo = true;
        } else {
            // Run out of chance, undo button cannot be clicked
            return false;
        }

        Memento moveRecord;
        // Undo both sides move
        for (int i = 0; i < 2 * steps; ++i) {
            moveRecord = careTaker.getMemento();
            restoreFromMomento(moveRecord);

        }
        return true;
    }

    /**
     * Requires:
     * 1. Call before the move actually happened, but after the user confirmed
     * 2. Move should be valid
     *
     * @param move
     * @param piece
     */
    public void recordMoveBeforeAction(Move move, Piece piece) {
        HashMap<int[], Player> paintBeforeChange = new HashMap<>();
        // Record the board info before the changes
        for (int[] paint : move.getPaintShape().getPaintInfo()) {
            paintBeforeChange.put(paint,
                                  getSquareAt(paint[0], paint[1]).getOccupiedPlayer());
        }
        originator.setMoveAndPiece(move, piece);
        originator.setPaintBeforeMove(paintBeforeChange);
    }

    @Override
    public Map<Piece, Square> getPieceSquareMap() {
        return pieceSquareMap;
    }

    @Override
    public Square[][] getSquares() {
        return squares;
    }

    @Override
    public int getTotalRows() {
        return totalRows;
    }

    @Override
    public int getTotalCols() {
        return totalCols;
    }

    // endregion

    // region private methods
    private void initBoard() {
        initSquare();
        initPieces(sharkNums, eagleNums);
        initObstacles();

        eventListener.onBoardInitialised(totalRows, totalCols, pieceSquareMap.keySet(), obstacleList);
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
    @Override
    public boolean isSquareValid(int[] position, Piece movingPiece) {
        if (isPositionOutOfBound(position)) {
            return false;
        }

        Square square = getSquareAt(position[0], position[1]);
        return !isOccupiedBySameTeam(position, movingPiece) && !square.isBlocked(movingPiece);
    }

    private boolean isOccupiedBySameTeam(int[] position, Piece movingPiece) {
        Piece pieceOnSquare = getSquareAt(position[0], position[1]).getPiece();

        if (pieceOnSquare == null || movingPiece == null) {
            return false;
        }
        return movingPiece.isBelongTo(pieceOnSquare.getTeam());
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
    private void updatePiecePosition(Move move, Piece piece) {
        int[] startPos = move.getRoute().get(0);
        int[] destinationPos = move.getFinalPosition();

        Square start = getSquareAt(startPos[0], startPos[1]);
        start.setPiece(null);

        Square destination = getSquareAt(destinationPos[0], destinationPos[1]);

        if (destination.getPiece() != null) {
            attackPiece(destination.getPiece(), destination);
        }

        destination.setPiece(piece);

        pieceSquareMap.put(piece, destination);
    }

    /**
     * Requires:
     * 1. move != null
     * 2. player != null
     * <p>
     * Ensures:
     * 1. squares of PaintInfo are occupied by the player
     */
    private void updateTerritory(Move move, Player player) {
        for (int[] position : move.getPaintShape().getPaintInfo()) {
            Square square = getSquareAt(position[0], position[1]);
            if (square.getPiece() != null) {
                if (player == Player.EAGLE &&
                        !(square.getPiece().isBelongTo(Player.EAGLE))) {
                    attackPiece(square.getPiece(), square);
                } else if (player == Player.SHARK &&
                        !(square.getPiece().isBelongTo(Player.SHARK))) {
                    attackPiece(square.getPiece(), square);
                }
            }
            square.setOccupiedPlayer(player);
        }
    }

    @Override
    public List<int[]> getSharksPositions() {
        List<int[]> list = new ArrayList<>();

        for (Piece piece : pieceSquareMap.keySet()) {
            if (piece.isBelongTo(Player.SHARK)) {
                Square square = pieceSquareMap.get(piece);
                int row = square.getRow();
                int col = square.getCol();
                list.add(new int[] {row, col});
            }
        }
        return list;
    }

    private void restoreFromMomento(Memento memento) {
        LinkedList<CustomPieceMove> reversePieceMove;
        // shark paint
        memento.getReversePaint().pop();
        // TODO: a better way to restore colour

        CustomPieceMove reversePiece;

        if (!memento.getPieceReverseInfo().isEmpty()) {
            reversePieceMove = memento.getPieceReverseInfo();
            while (reversePieceMove.size() != 0) {
                reversePiece = reversePieceMove.pop();
            }
        }
    }

    private void attackPiece(Piece attackedPiece, Square attackedSquare) {
        int[] newPos = new int[2];
        newPos[0] = attackedPiece.getStartPos()[0];

        do {
            newPos[1] = genRandomCol();
            // TODO: record the startPosition
        } while (!isSquareValid(newPos, attackedPiece));

        Square startSquare = getSquareAt(newPos[0], newPos[1]);
        startSquare.setPiece(attackedPiece);

        attackedSquare.setPiece(null);

        pieceSquareMap.put(attackedPiece, startSquare);

        eventListener.updatePiecePosition(attackedSquare.getRow(), attackedSquare.getCol(), newPos[0], newPos[1]);
    }

    private int genRandomCol() {
        return new Random().nextInt(totalCols - 0) + 0;
    }

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

        addPlayerPieces(eagles, 0, Player.EAGLE);
        addPlayerPieces(sharks, totalRows - 1, Player.SHARK);
    }

    private void initObstacles() {
        obstacleList = new ArrayList<>();
        obstacleList.addAll(BoardHelper.generateObstacles(ObstacleType.ROCK, 0.05, this));
        obstacleList.addAll(BoardHelper.generateObstacles(ObstacleType.TREE, 0.10, this));
        obstacleList.addAll(BoardHelper.generateObstacles(ObstacleType.FOREST, 0.05, this));
    }

    private void addPlayerPieces(int totalPieces, int row, Player player) {
        // Gaps between each piece
        int split = totalCols / totalPieces;
        int typesOfPieces = 3;
        int[] numOfEachTypePiece = calculateNumOfPieces(totalPieces, typesOfPieces);

        int index = 0;
        int col = totalCols / totalPieces;
        for (PieceType pieceType : PieceType.values()) {
            if (pieceType.isBelongTo(player)) {
                for (int i = 0; i < numOfEachTypePiece[index % typesOfPieces]; i++) {
                    Piece piece = pieceType.createPiece(row, col - 1);
                    col += split;
                    addPieceIntoSquare(piece);
                }
            }
            index++;
        }
    }

    private void addPieceIntoSquare(Piece piece) {
        int[] pos = piece.getStartPos();
        Square square = getSquareAt(pos[0], pos[1]);
        square.setPiece(piece);
        pieceSquareMap.put(piece, square);
    }

    private int[] calculateNumOfPieces(int totalPieces, int typesOfPieces) {
        int[] piecesNums = new int[typesOfPieces];
        Arrays.fill(piecesNums, totalPieces / typesOfPieces);

        for (int i = 0; i < totalPieces % typesOfPieces; i++) {
            piecesNums[typesOfPieces - 1 - i] += 1;
        }
        return piecesNums;
    }

    /**
     * Requires:
     * 1. row >= 0 && col >= 0
     * 2. row < ROW && col < COL
     */
    public Square getSquareAt(int row, int col) {
        return squares[row][col];
    }

    @Override
    public void onPieceSelected(Piece piece, int row, int col) {
        chosenPiece = piece;
        eventListener.onPieceSelected(row, col);
    }

    @Override
    public void onMoveButtonClicked(Move move, Player currentPlayer) {
        updatePiecePosition(move, chosenPiece);
        updateTerritory(move, currentPlayer);
        chosenPiece = null;

        eventListener.onPieceMoved(move, currentPlayer);
    }

    @Override
    public int[] getPiecePosition(Piece chosenPiece) {
        Square square = pieceSquareMap.get(chosenPiece);
        return new int[] {square.getRow(), square.getCol()};
    }

    @Override
    public void timeRantOut() {
        chosenPiece = null;
        eventListener.onTimeRanOut();
    }

    /**
     * Requires:
     * 1. position != null
     */
    @Override
    public boolean isPositionOutOfBound(int[] position) {
        return position[0] < 0 || position[0] >= totalRows ||
                position[1] < 0 || position[1] >= totalCols;
    }
    // endregion

    // region Board Model Event Listener interface
    public interface BoardModelEventListener {
        void onBoardInitialised(int totalRows, int totalCols, Set<Piece> keySet,
                                List<Square> obstacleList);

        void updatePiecePosition(int attackedRow, int attackedCol, int resetRow, int resetCol);

        void onPieceSelected(int row, int col);

        void onPieceMoved(Move move, Player turnCount);

        void onTimeRanOut();
    }
    // endregion

}
