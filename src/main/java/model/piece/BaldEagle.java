package main.java.model.piece;

import main.java.model.move.BaldEagleMove;
import main.java.model.move.Move;

import java.util.List;

package main.java.model.move;

public class BaldEagle extends Eagle {
    private final int MOVE_ONE_SQUARE = 1;
    private final int MOVE_TWO_SQUARES = 2;
    private final int MOVE_THREE_SQUARES = 3;

    private String[] directions = {DIRECTION_DOWN, DIRECTION_UP, DIRECTION_RIGHT, DIRECTION_LEFT};
    private int[] numSquaresMoved = {MOVE_ONE_SQUARE, MOVE_TWO_SQUARES, MOVE_THREE_SQUARES};

    // moves forward 1-3 squares (eg. [4,4] -> [8,4]
    // left, right and forward
    // makes 9 movements

    // assume start [4,4]
    // move 1 - [5,4], [3,4], [4,5], [4,3]
    // move 2 - [6,4], [2,4], [4,6], [4,2]
    // move 3 - [7,4], [1,4], [4,7], [4,1]

    public BaldEagle() {
        super();
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        for (String direction : directions) {
            for (int squaresMoved : numSquaresMoved) {
                Move move = new BaldEagleMove(startRow, startCol, squaresMoved, direction);
                allMovesList.add(move);
            }
        }
        return allMovesList;
    }

}
