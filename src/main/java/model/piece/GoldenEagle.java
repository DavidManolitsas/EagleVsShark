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
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        // but since we have 3 sharks we should expect only 3 positions right now?
        for (int[] sharksPos : sharkPosList) {
            int[] sharkPos = sharksPos;
            Move move = movePiece(startRow, startCol, sharkPos);
            allMovesList.add(move);
        }

        return allMovesList;
    }

    private Move movePiece(int startRow, int startCol, int[] sharkPos) {
        Move move = new GoldenEagleMove(startRow, startCol, sharkPos);
        return move;
    }

    public void setSharkList(List<int[]> sharksPos) {
        this.sharkPosList = sharksPos;
    }


}
