package main.java.model.piece;

import main.java.model.move.Move;

import java.util.Collections;
import java.util.List;

public class BaldEagle extends Eagle {
    private final int MOVE_SQUARE_ONE = 1;
    private final int MOVE_SQUARE_TWO = 2;
    private final int MOVE_SQUARE_THREE = 3;

//    private final String DIRECTION_BACK = "backward";
//    private final String DIRECTION_FOWARD = "forward";
//    private final String DIRECTION_LEFT = "left";
//    private final String DIRECTION_RIGHT = "right";

    // moves forward 1-3 squares (eg. [4,4] -> [8,4]
    // left, right and forward
    // makes 9 movements

    // assume start [4,4]
    // move 1 - [5,4], [3,4], [4,5], [4,3]
    // move 2 - [6,4], [2,4], [4,6], [4,2]
    // move 3 - [7,4], [1,4], [4,7], [4,1]

    public BaldEagle() {
        super();

//        this.moveList = getMoveList();
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        Move moveOneRight = movePiece(startRow, startCol, MOVE_SQUARE_ONE, DIRECTION_RIGHT);
        Move moveTwoRight = movePiece(startRow, startCol, MOVE_SQUARE_TWO, DIRECTION_RIGHT);
        Move moveThreeRight = movePiece(startRow, startCol, MOVE_SQUARE_THREE, DIRECTION_RIGHT);

//        Move moveOneLeft = moveLeft(startRow, startCol, MOVE_SQUARE_ONE);
//        Move moveTwoLeft = moveLeft(startRow, startCol, MOVE_SQUARE_TWO);
//        Move moveThreeLeft = moveLeft(startRow, startCol, MOVE_SQUARE_THREE);

        Move moveOneLeft = movePiece(startRow, startCol, MOVE_SQUARE_ONE, DIRECTION_LEFT);
        Move moveTwoLeft = movePiece(startRow, startCol, MOVE_SQUARE_TWO, DIRECTION_LEFT);
        Move moveThreeLeft = movePiece(startRow, startCol, MOVE_SQUARE_THREE, DIRECTION_LEFT);

        Move moveOneDown = movePiece(startRow, startCol, MOVE_SQUARE_ONE, DIRECTION_DOWN);
        Move moveTwoDown = movePiece(startRow, startCol, MOVE_SQUARE_TWO, DIRECTION_DOWN);
        Move moveThreeDown = movePiece(startRow, startCol, MOVE_SQUARE_THREE, DIRECTION_DOWN);

        Move moveOneUp = movePiece(startRow, startCol, MOVE_SQUARE_ONE, DIRECTION_UP);
        Move moveTwoUp = movePiece(startRow, startCol, MOVE_SQUARE_TWO, DIRECTION_UP);
        Move moveThreeUp = movePiece(startRow, startCol, MOVE_SQUARE_THREE, DIRECTION_UP);


        List<Move> moveList = getAllMovesList();

        // is there a better way of adding objects to a list?
        Collections.addAll(moveList, moveOneDown, moveOneUp, moveOneLeft, moveOneRight, moveTwoDown, moveTwoUp, moveTwoLeft, moveTwoRight, moveThreeDown, moveThreeUp, moveThreeLeft, moveThreeRight, );
        return moveList;
    }


    public Move movePiece(int startRow, int startCol, int numSquaresMoved, String direction) {
        Move move = new BaldEagleMove(startRow, startCol, numSquaresMoved, direction);
        return move;
    }

//    public Move moveLeft(int startRow, int startCol, int numSquaresMoved) {
//        Move move = new Move(startRow, startCol, numSquaresMoved, DIRECTION_LEFT);
//        return move;
//    }
//
//    public Move moveForward(int startRow, int startCol, int numSquaresMoved) {
//        Move move = new Move(startRow, startCol, numSquaresMoved, DIRECTION_FOWARD);
//        return move;
//    }
//
//    public Move moveBack(int startRow, int startCol, int numSquaresMoved) {
//        Move move = new Move(startRow, startCol, numSquaresMoved, DIRECTION_BACK);
//        return move;
//    }
}
