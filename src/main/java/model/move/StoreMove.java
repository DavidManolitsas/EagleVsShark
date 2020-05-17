package main.java.model.move;

import main.java.model.piece.Piece;
import main.java.model.player.Player;

import java.util.Map;
import java.util.List;

public class StoreMove {
    private Move move;
    private Piece piece;
    private Map<int[], Player> paintBeforeChange;

    public StoreMove(Move move, Piece piece, Map<int[], Player> paintBeforeChange) {
        this.move = move;
        this.piece = piece;
        this.paintBeforeChange = paintBeforeChange;
    }

    public int[] getFinalPosition() {
        return move.getFinalPosition();
    }

    public int[] getStartPosition() {
        return move.getPaintShape().getPaintInfo().get(0);
    }

    public List<int[]> getPaintInfo() {
        return move.getPaintShape().getPaintInfo();
    }
}
