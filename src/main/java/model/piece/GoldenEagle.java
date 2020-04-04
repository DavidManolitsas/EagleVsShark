package main.java.model.piece;

import main.java.model.move.GoldenEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class GoldenEagle extends Eagle {
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

        int[] sharkPos1 = getSharkPos(0);
        int[] sharkPos2 = getSharkPos(1);
        int[] sharkPos3 = getSharkPos(2);

        Move move1 = movePiece(startRow, startCol, sharkPos1);
        Move move2 = movePiece(startRow, startCol, sharkPos2);
        Move move3 = movePiece(startRow, startCol, sharkPos3);

        List<Move> moveList = getAllMovesList();

//        for (int i = 0; i < moveList.size(); i++) {
        moveList.add(move1);
        moveList.add(move2);
        moveList.add(move3);
//        }
        return moveList;
    }

    private Move movePiece(int startRow, int startCol, int[] sharkPos) {
        Move move = new GoldenEagleMove(startRow, startCol, sharkPos);
        return move;
    }

    private int[] getSharkPos(int index) {
        int[] sharkPos = sharkPosList.get(index);
        return sharkPos;
    }

    public void setSharkList(List<int[]> sharksPos) {
        this.sharkPosList = sharksPos;
    }


}
