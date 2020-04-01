package main.java.model.piece;

import main.java.model.move.Move;

import java.util.ArrayList;
import java.util.List;

abstract class Piece {
    private List<Move> allMovesList;

    public Piece() {
        allMovesList = new ArrayList<Move>();
    }

    public abstract List<Move> getAllMoves(int startRow, int startCol);

    public List<Move> getMoveList() {
        return allMovesList;
    }


}
