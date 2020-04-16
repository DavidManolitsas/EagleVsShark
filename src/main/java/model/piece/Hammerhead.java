package main.java.model.piece;

import main.java.model.move.HammerheadMove;
import main.java.model.move.Move;

import java.util.List;


public class Hammerhead
        extends Shark {

    public Hammerhead(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * @param startRow >=0 && <= 14
     * @param startCol >= 0 && <= 9
     * @return array list of hammerhead move objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new HammerheadMove(startRow, startCol).getMoveList();

    }

}
