package main.java.model.move;

import java.util.ArrayList;
import java.util.List;

import main.java.model.move.movements.Movements;

public abstract class PieceMove {
    protected List<Move> moveList;
    private int squaresMoved;
    private Movements[] movements;

    public PieceMove(int squaresMoved) {
        this.squaresMoved = squaresMoved;
        moveList = new ArrayList<>();
    }

    public List<Move> getMoveList() {
        return moveList;
    }

}
