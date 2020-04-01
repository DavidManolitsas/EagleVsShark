package main.java.model.piece;

import main.java.model.move.Move;

import java.util.List;

public class BaldEagle extends Eagle {
//    private final int NUM_SQUARES_MOVED;
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
//        Move move1 = new Move(startRow, startCol, NUM_SQUARES_MOVED);
//        Move move2 = new Move(startRow, startCol);
//        Move move3 = new Move(startRow, startCol);

        List<Move> moveList = getMoveList();

        for (int i = 0; i < moveList.size(); i++) {
//            moveList.add(move1);
//            moveList.add(move2);
//            moveList.add(move3);
        }
        return moveList;
    }

//    public List<Move> getMoveList() {
//        return super.allMoves;
//    }
}
