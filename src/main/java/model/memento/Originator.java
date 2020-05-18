package main.java.model.memento;

import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.model.player.Player;

import java.util.HashMap;
import java.util.LinkedList;

public class Originator {
    protected Move currentMove;
    protected Piece actionPiece;
    protected LinkedList<Piece> movedPieces;
    protected LinkedList<int[]> previousLocations;
    protected LinkedList<int[]> newLocations;
    protected HashMap<int[], Player> paintBeforeChange;

    public Originator() {
        currentMove = null;
        actionPiece = null;
        movedPieces = new LinkedList<Piece>();
        previousLocations = new LinkedList<int[]>();
        newLocations = new LinkedList<int[]>();
        paintBeforeChange = new HashMap<int[], Player>();
    }

    // record changes before the move
    public void setPaintBeforeMove(HashMap<int[], Player> paintBeforeChange) {
        this.paintBeforeChange = paintBeforeChange;
    }

    // record the move perform
    public void setMoveAndPiece(Move currentMove, Piece actionPiece) {
        this.currentMove = currentMove;
        this.actionPiece = actionPiece;
    }

    // record piece before and after move
    public void addAttackPieceChange(Piece piece, int[] previousLocation, int[] newLocation) {
        movedPieces.push(piece);
        previousLocations.push(previousLocation);
        newLocations.push(newLocation);

    }

    public Memento create() {
        Memento memento = new Memento(currentMove, actionPiece, paintBeforeChange, movedPieces, previousLocations, newLocations);
        // reset state
        currentMove = null;
        actionPiece = null;
        movedPieces = new LinkedList<Piece>();
        previousLocations = new LinkedList<int[]>();
        newLocations = new LinkedList<int[]>();
        paintBeforeChange = new HashMap<int[], Player>();
        return memento;
    }
}
