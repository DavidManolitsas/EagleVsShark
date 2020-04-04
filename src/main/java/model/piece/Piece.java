package main.java.model.piece;

import main.java.model.move.Move;

import java.util.ArrayList;
import java.util.List;

abstract class Piece {
    private List<Move> allMovesList;
    public static final String DIRECTION_DOWN = "down";
    public static final String DIRECTION_UP = "up";
    public static final String DIRECTION_LEFT = "left";
    public static final String DIRECTION_RIGHT = "right";
    public static final String DIRECTION_DIAGONAL_RIGHT = "diagonal right";
    public static final String DIRECTION_DIAGONAL_LEFT = "diagonal left";

    public Piece() {
        allMovesList = new ArrayList<Move>();
    }

    public abstract List<Move> getAllMoves(int startRow, int startCol);

    public List<Move> getAllMovesList() {
        return allMovesList;
    }


}
