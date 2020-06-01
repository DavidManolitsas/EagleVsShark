package main.java.util;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import main.java.model.Player;
import main.java.model.Square;
import main.java.model.board.Board;
import main.java.model.obstacles.ObstacleType;
import main.java.model.piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author WeiYi Yu
 * @date 2020-05-26
 */
public class BoardHelper {

    public static List<Square> generateObstacles(ObstacleType obstacleType,
                                                 double percentageNumObstacles,
                                                 Board board) {

        List<Square> obstacleList = new ArrayList<>();

        int totalObstacles = (int) (percentageNumObstacles * (board.getTotalRows() * board.getTotalCols()));
        Random random = new Random();

        while (obstacleList.size() < totalObstacles) {
            int row = random.nextInt(board.getTotalRows());
            int col = random.nextInt(board.getTotalCols());

            int[] position = new int[] {row, col};

            if (isSquareValid(position, null, board)) {
                Square square = board.getSquareAt(row, col);
                if (square.getPiece() == null) {
                    square.setObstacle(obstacleType);
                    obstacleList.add(square);
                }
            }
        }
        return obstacleList;
    }

    @Requires("totalPieces >= 3 && typesOfPieces >= 3")
    @Ensures("piecesNums.length == typesOfPieces")
    public static int[] calculateNumOfPieces(int totalPieces, int typesOfPieces) {
        int[] piecesNums = new int[typesOfPieces];
        Arrays.fill(piecesNums, totalPieces / typesOfPieces);

        for (int i = 0; i < totalPieces % typesOfPieces; i++) {
            piecesNums[typesOfPieces - 1 - i] += 1;
        }
        return piecesNums;
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
    public static boolean isSquareValid(int[] position, Piece movingPiece, Board board) {
        if (isPositionOutOfBound(position, board)) {
            return false;
        }

        Square square = board.getSquareAt(position[0], position[1]);
        return !isOccupiedBySameTeam(position, movingPiece, board) && !square.isBlocked(movingPiece);
    }

    public static boolean isPositionOutOfBound(int[] position, Board board) {
        return position[0] < 0 || position[0] >= board.getTotalRows() ||
                position[1] < 0 || position[1] >= board.getTotalCols();
    }

    public static boolean isOccupiedBySameTeam(int[] position, Piece movingPiece, Board board) {
        Piece pieceOnSquare = board.getSquareAt(position[0], position[1]).getPiece();

        if (pieceOnSquare == null || movingPiece == null) {
            return false;
        }
        return movingPiece.isBelongTo(pieceOnSquare.getTeam());
    }

    public static int getPlayerScore(Player player, Board board) {
        Square[][] squares = board.getSquares();
        int count = 0;
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (board.getSquareAt(row, col).getOccupiedPlayer() == player) {
                    count++;
                }
            }
        }
        return count;
    }
}
