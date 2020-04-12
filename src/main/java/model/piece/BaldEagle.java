package main.java.model.piece;


import main.java.model.move.BaldEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class BaldEagle extends Eagle {
    private final int MOVE_ONE_SQUARE = 1;
    private final int MOVE_TWO_SQUARES = 2;
    private final int MOVE_THREE_SQUARES = 3;

    private String[] directions = {DIRECTION_DOWN, DIRECTION_RIGHT, DIRECTION_LEFT};
    private int[] numSquaresMoved = {MOVE_ONE_SQUARE, MOVE_TWO_SQUARES, MOVE_THREE_SQUARES};

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
