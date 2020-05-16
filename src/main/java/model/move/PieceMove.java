package main.java.model.move;

import java.util.ArrayList;
import java.util.List;

public abstract class PieceMove {
    protected List<Move> moveList;

    public PieceMove() {
        moveList = new ArrayList<>();
    }

    public List<Move> getMoveList() {
        return moveList;
    }

}
