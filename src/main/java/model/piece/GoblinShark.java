package main.java.model.piece;

import java.util.List;

public class GoblinShark extends Shark {
    public GoblinShark(int startRow, int startCol) {
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
}
