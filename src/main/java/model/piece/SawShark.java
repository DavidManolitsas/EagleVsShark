package main.java.model.piece;

import main.java.model.move.Move;
import main.java.model.move.SawSharkMove;

import java.util.List;

public class SawShark
        extends Shark {

    public SawShark(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * @param startRow >=0 && <= 14
     * @param startCol >= 0 && <= 9
     * @return array list of saw shark move objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new SawSharkMove(startRow, startCol).getMoveList();
    }

}
