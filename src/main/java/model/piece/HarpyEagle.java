package main.java.model.piece;

import main.java.model.move.HarpyEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class HarpyEagle
        extends Eagle {

    public HarpyEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }
    // flies forward, back, left or right 3 spaces
    // moves 4 times

    // start [4,4]
    // move 1 = [7,4]
    // move 2 = [1,4]
    // move 3 = [4, 7]
    // move 4 = [4,1]

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new HarpyEagleMove(startRow, startCol).getMoveList();
    }

}
