package main.java.model.piece;

import main.java.model.move.HarpyEagleMove;
import main.java.model.move.Move;

import java.util.Collections;
import java.util.List;

public class HarpyEagle extends Eagle {
    private final int NUM_SQUARES_MOVED = 3;
    // flies forward, back, left or right 3 spaces
    // moves 4 times

    // start [4,4]
    // move 1 = [7,4]
    // move 2 = [1,4]
    // move 3 = [4, 7]
    // move 4 = [4,1]

    public HarpyEagle() {
        super();
//        this.moveList = getMoveList();
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        Move move1 = movePiece(startRow, startCol, DIRECTION_DOWN);
        Move move2 = movePiece(startRow, startCol, DIRECTION_UP);
        Move move3 = movePiece(startRow, startCol, DIRECTION_RIGHT);
        Move move4 = movePiece(startRow, startCol, DIRECTION_LEFT);

        List<Move> moveList = getAllMovesList();

        Collections.addAll(moveList, move1, move2, move3, move4);

        return moveList;
    }


    public Move movePiece(int startRow, int startCol, String direction) {
        Move move = new HarpyEagleMove(startRow, startCol, NUM_SQUARES_MOVED, direction);
        return move;
    }
}
