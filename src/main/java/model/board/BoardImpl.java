package main.java.model.board;


import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;
import main.java.model.Player;
import main.java.model.Square;
import main.java.model.commands.AttackPieceInfo;
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
 * pieceSquareMap.size == 6
 */
@Invariant("square != null && pieceSquareMap != null")
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

    private List<Square> obstacleList;

    private AttackPieceInfo attackPieceInfo;

    public BoardImpl(BoardModelEventListener boardListener, int rows, int cols,
                     int sharks, int eagles) {
        this.eventListener = boardListener;
        totalRows = rows;
        totalCols = cols;
        sharkNums = sharks;
        eagleNums = eagles;
        chosenPiece = null;

        initBoard();
    }

    // region public Board methods
    @Requires("row >= 0 && row < totalRows && col >= 0 && col < totalCols")
    @Override
    public Piece getPiece(int row, int col) {
        return getSquareAt(row, col).getPiece();
    }

    @Override
    public Piece getChosenPiece() {
        return chosenPiece;
    }

    @Requires("row >= 0 && row < totalRows && col >= 0 && col < totalCols")
    @Override
    public Square getSquareAt(int row, int col) {
        return squares[row][col];
    }

    @Override
    public void onPieceSelected(Piece piece, int row, int col) {
        chosenPiece = piece;
        eventListener.onPieceSelected(row, col);
    }

    @Override
    public AttackPieceInfo onMoveButtonClicked(Move move, Player currentPlayer) {
        attackPieceInfo = new AttackPieceInfo();
        updatePiecePosition(move, chosenPiece);
        updateTerritory(move, currentPlayer);
        chosenPiece = null;
        eventListener.onPieceMoved(move, currentPlayer);
        return attackPieceInfo;
    }

    @Override
    public void undoMove(Move move, Piece piece, Player currentPlayer, List<Player> occupiedPlayerHistory, AttackPieceInfo attackPieceInfo) {
        revertPiecePosition(move, piece);
        revertTerritory(move, occupiedPlayerHistory);
        if (!attackPieceInfo.getAttackedPieces().isEmpty()) {
            revertAttackedPiece(attackPieceInfo);
        }
        eventListener.onUndoMove(move, occupiedPlayerHistory, attackPieceInfo);

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
    // endregion

    // region private methods
    private void initBoard() {
        initSquare();
        initPieces(sharkNums, eagleNums);
        initObstacles();

        eventListener.onBoardInitialised(totalRows, totalCols, pieceSquareMap.keySet(), obstacleList);
    }

    @Requires("move != null && piece != null")
    @Ensures("pieceSquareMap.get(piece) == destination && start.getPiece() == null")
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

    @Requires("move != null && player != null")
    @Ensures("square.getOccupiedPlayer() == player")
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

    @Requires("attackedPiece != null && attackedSquare != null")
    @Ensures("pieceSquareMap.get(attackedPiece) == startSquare && attackedSquare.getPiece() == null")
    private void attackPiece(Piece attackedPiece, Square attackedSquare) {
        int[] newPos = new int[2];
        newPos[0] = attackedPiece.getStartPos()[0];

        do {
            newPos[1] = genRandomCol();
        } while (!BoardHelper.isSquareValid(newPos, attackedPiece, this));

        Square startSquare = getSquareAt(newPos[0], newPos[1]);
        startSquare.setPiece(attackedPiece);

        attackedSquare.setPiece(null);

        attackPieceInfo.addAttackedPiece(attackedPiece,
                new int[]{attackedSquare.getRow(), attackedSquare.getCol()},
                newPos);

        pieceSquareMap.put(attackedPiece, startSquare);

        eventListener.updatePiecePosition(attackedSquare.getRow(), attackedSquare.getCol(), newPos[0], newPos[1]);
    }

    private int genRandomCol() {
        return new Random().nextInt(totalCols - 0) + 0;
    }

    @Ensures("squares != null")
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
        int[] numOfEachTypePiece = BoardHelper.calculateNumOfPieces(totalPieces, typesOfPieces);

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

    private void revertPiecePosition(Move move, Piece piece) {
        int[] startPos = move.getReverseRoute().get(0);
        int[] destinationPos = move.getReverseRoute().get(1);
        setPieceToSquare(startPos, destinationPos, piece);
    }

    private void setPieceToSquare(int[] startPos, int[] destinationPos, Piece piece) {
        Square start = getSquareAt(startPos[0], startPos[1]);
        start.setPiece(null);

        Square destination = getSquareAt(destinationPos[0], destinationPos[1]);

        destination.setPiece(piece);

        pieceSquareMap.put(piece, destination);
    }

    private void revertTerritory(Move move, List<Player> occupiedPlayerHistory) {
        int i = 0;
        for (int[] position : move.getPaintShape().getPaintInfo()) {
            Square square = getSquareAt(position[0], position[1]);
            square.setOccupiedPlayer(occupiedPlayerHistory.get(i++));
        }
    }

    private void revertAttackedPiece(AttackPieceInfo attackPieceInfo) {
        for (int i = 0; i < attackPieceInfo.getAttackedPieces().size(); ++i) {
            setPieceToSquare(attackPieceInfo.getNewPositions().get(i),
                    attackPieceInfo.getPreviousPositions().get(i),
                    attackPieceInfo.getAttackedPieces().get(i));
        }
    }

    @Requires("piece != null")
    @Ensures("pieceSquareMap.size() = (old(pieceSquareMap.size()) + 1) && square.getPiece() == piece")
    private void addPieceIntoSquare(Piece piece) {
        int[] pos = piece.getStartPos();
        Square square = getSquareAt(pos[0], pos[1]);
        square.setPiece(piece);
        pieceSquareMap.put(piece, square);
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

        void onUndoMove(Move move, List<Player> turnCount, AttackPieceInfo attackPieceInfo);
    }
    // endregion
}
