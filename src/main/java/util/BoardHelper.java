package main.java.util;

import main.java.model.Square;
import main.java.model.board.Board;
import main.java.model.obstacles.ObstacleType;

import java.util.ArrayList;
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

            if (board.isSquareValid(position, null)) {
                Square square = board.getSquareAt(row, col);
                if (square.getPiece() == null) {
                    square.setObstacle(obstacleType);
                    obstacleList.add(square);
                }
            }
        }
        return obstacleList;
    }
}
