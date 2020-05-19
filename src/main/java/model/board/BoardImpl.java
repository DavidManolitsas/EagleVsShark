package main.java.model.board;

import main.java.model.Square;
import main.java.model.memento.CareTaker;
import main.java.model.memento.Memento;
import main.java.model.memento.Originator;
import main.java.model.move.CustomPieceMove;
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

        if (player instanceof EaglePlayer && !isEagleUndo) {
            isEagleUndo = true;
        } else if (player instanceof SharkPlayer && !isSharkUndo) {
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
        HashMap<int[], Player> paintBeforeChange = new HashMap<int[], Player>();
        // Record the board info before the changes
        for (int[] paint : move.getPaintShape().getPaintInfo()) {
            paintBeforeChange.put(paint,
                                  getSquareAt(paint[0], paint[1]).getOccupiedPlayer());
        }
        originator.setMoveAndPiece(move, piece);
        originator.setPaintBeforeMove(paintBeforeChange);
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
    // endregion

    // region private methods
    private void initBoard() {
        initSquare();
        initPieces(sharkNums, eagleNums);

        eventListener.onBoardInitialised(totalRows, totalCols, squares[0], squares[totalRows - 1]);
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
    private boolean isSquareValid(int[] position) {
        if (isPositionOutOfBound(position)) {
            return false;
        }

        // Check if the square already has piece on it
        Piece piece = getSquareAt(position[0], position[1]).getPiece();
        return piece == null ||
                (chosenPiece != null &&
                        !piece.getClass().getSuperclass().equals(chosenPiece.getClass().getSuperclass()));
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
                if (player instanceof EaglePlayer && !(square.getPiece() instanceof Eagle)) {
                    attackPiece(square.getPiece(), square);
                } else if (player instanceof SharkPlayer && !(square.getPiece() instanceof Shark)) {
                    attackPiece(square.getPiece(), square);
                }
            }
            square.setOccupiedPlayer(player);
        }
    }

    private List<int[]> getSharksPositions() {
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
        } while (!isSquareValid(newPos));

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

        int typesOfPieces = 3;
        int topRow = 0;
        int bottomRow = totalRows - 1;
        // Gaps between each piece
        int sharkSplit = totalCols / sharks;
        int sharkPosCount = sharkSplit;
        int eagleSplit = totalCols / eagles;
        int eaglePosCount = eagleSplit;

        // Get each number of sharks
        int goblinSharks, sawSharks, hammerHeads, goldenEagles, baldEagles, harpyEagles;
        if (sharks % typesOfPieces == 0) {
            goblinSharks = sharks / typesOfPieces;
            sawSharks = sharks / typesOfPieces;
            hammerHeads = sharks / typesOfPieces;
        } else if (sharks % typesOfPieces == 1) {
            goblinSharks = sharks / typesOfPieces;
            sawSharks = sharks / typesOfPieces;
            hammerHeads = sharks / typesOfPieces + 1;
        } else {
            goblinSharks = sharks / typesOfPieces;
            sawSharks = sharks / typesOfPieces + 1;
            hammerHeads = sharks / typesOfPieces + 1;
        }

        //  Get each number of eagles
        if (eagles % typesOfPieces == 0) {
            goldenEagles = eagles / typesOfPieces;
            baldEagles = eagles / typesOfPieces;
            harpyEagles = eagles / typesOfPieces;
        } else if (eagles % typesOfPieces == 1) {
            goldenEagles = eagles / typesOfPieces;
            baldEagles = eagles / typesOfPieces;
            harpyEagles = eagles / typesOfPieces + 1;
        } else {
            goldenEagles = eagles / typesOfPieces;
            baldEagles = eagles / typesOfPieces + 1;
            harpyEagles = eagles / typesOfPieces + 1;
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

        if (piece instanceof GoldenEagle) {
            List<int[]> sharksPositions = getSharksPositions();
            ((GoldenEagle) piece).setSharkList(sharksPositions);
        }

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

    // region Board Model Event Listener interface
    public interface BoardModelEventListener {
        void onBoardInitialised(int rows, int cols, Square[] topRow, Square[] bottomRow);

        void updatePiecePosition(int attackedRow, int attackedCol, int resetRow, int resetCol);

        void onPieceSelected(int row, int col);

        void onPieceMoved(Move move, Player turnCount);

        void onTimeRanOut();
    }
    // endregion

}
