package main.java.model.piece;

import main.java.model.move.GoblinSharkMove;
import main.java.model.move.Move;

import java.util.List;


public class GoblinShark
        extends Shark {

    public GoblinShark(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * @param startRow >=0 && <= 14
     * @param startCol >= 0 && <= 9
     * @return array list of goblin shark move objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new GoblinSharkMove(startRow, startCol).getMoveList();
    }

}

