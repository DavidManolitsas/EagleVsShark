package main.java.model.piece;

import main.java.model.move.GoldenEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class GoldenEagle
        extends Eagle {
    private List<int[]> sharkPosList;

    public GoldenEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }
    // flies to behind any shark
    // 3 movements then

    // assume shark1 = [4,4], shark2 = [8,7], shark3 = [10, 4]
    // assume shark pos = [i,j]
    // move behind shark [i-1,j]

    /**
     * @param startRow >=0 && <= 14
     * @param startCol >= 0 && <= 9
     * @return array list of golden eagle move objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new GoldenEagleMove(startRow, startCol, sharkPosList).getMoveList();
    }

    /**
     * @param sharksPos list of size 2 int arrays
     *                  ensures the assignment of a List to sharkPosList
     */
    public void setSharkList(List<int[]> sharksPos) {
        this.sharkPosList = sharksPos;
    }
}
