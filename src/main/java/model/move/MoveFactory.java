package main.java.model.move;

import java.util.ArrayList;
import java.util.List;

public abstract class MoveFactory {
    protected List<Move> moveList;

    public MoveFactory(){
        moveList = new ArrayList<>();
    }

    public List<Move> getMoveList(){
        return moveList;
    }
}
