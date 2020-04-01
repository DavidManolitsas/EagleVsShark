package main.java.model.piece;

import java.util.List;

public class HarpyEagle extends Eagle {
//    private final int NUM_SQUARES_MOVED;
//    this.NUM_SQUARES_MOVED = 3;
    // flies forward, back, left or right 3 spaces
    // moves 4 times

    // start [4,4]
    // move 1 = [7,4]
    // move 2 = [1,4]
    // move 3 = [4, 7],
    // move 4 = [4,1]

    public HarpyEagle(int startRow, int startCol) {
        super(startRow, startCol);
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
}
