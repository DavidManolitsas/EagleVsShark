package main.java.model.piece;

import main.java.model.move.Move;

import java.util.List;

public class GoldenEagle extends Eagle {
    //    private final int NUM_SQUARES_MOVED;
    private List<int[]> sharkPosList;
    // flies to behind any shark
    // 3 movements then


    // assume shark1 = [4,4], shark2 = [8,7], shark3 = [10, 4]
    // assume shark pos = [i,j]
    // move behind shark [i-1,j]

    public GoldenEagle() {
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

    private void getSharkPos() {
//        for (Shark shark : sharkPosList) {
        //
//        }
    }

    public void setSharkList(List<int[]> sharksPos) {
        this.sharkPosList = sharksPos;
    }


}
