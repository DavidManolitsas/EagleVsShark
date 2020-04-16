package main.java.model.piece;


import main.java.model.move.BaldEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class BaldEagle
        extends Eagle {

    public BaldEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * @param startRow >=0 && <= 14
     * @param startCol >= 0 && <= 9
     * @return array list of bald eagle move objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new BaldEagleMove(startRow, startCol).getMoveList();
    }

}
