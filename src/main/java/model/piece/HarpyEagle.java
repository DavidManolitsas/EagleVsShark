package main.java.model.piece;

import main.java.model.move.HarpyEagleMove;
import main.java.model.move.Move;

import java.util.List;

public class HarpyEagle
        extends Eagle {

    public HarpyEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }

    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new HarpyEagleMove(startRow, startCol).getMoveList();
    }

}
