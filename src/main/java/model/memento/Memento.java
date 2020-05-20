package main.java.model.memento;

import main.java.model.Player;
import main.java.model.move.CustomPieceMove;
import main.java.model.move.Move;
import main.java.model.move.shape.CustomShape;
import main.java.model.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Memento {
    private Move currentMove;
    private Piece actionPiece;
    private LinkedList<Piece> movedPieces;
    private LinkedList<int[]> previousLocations;
    private LinkedList<int[]> newLocations;
    private HashMap<int[], Player> paintBeforeChange;

    public Memento(Move currentMove, Piece actionPiece, HashMap<int[], Player> paintBeforeChange,
                   LinkedList<Piece> movedPieces, LinkedList<int[]> previousLocations, LinkedList<int[]> newLocations) {
        this.currentMove = currentMove;
        this.actionPiece = actionPiece;
        this.movedPieces = movedPieces;
        this.previousLocations = previousLocations;
        this.newLocations = newLocations;
        this.paintBeforeChange = paintBeforeChange;
    }

    // return the previous location of the pieces
    public LinkedList<CustomPieceMove> getPieceReverseInfo() {
        LinkedList<CustomPieceMove> reverseInfo = new LinkedList<CustomPieceMove>();
        if (!movedPieces.isEmpty()) {
            reverseInfo.add(new CustomPieceMove(movedPieces.pop(), previousLocations.pop(), newLocations.pop()));
        }
        return reverseInfo;
    }

    // return the previous paint info on the board, held by CustomShape,
    // the first element in the reversePaint is the indexes of empty squares
    // the second element is the indexes of eagle squares
    // the third element is the indexes of shark squares
    public LinkedList<CustomShape> getReversePaint() {
        LinkedList<CustomShape> reversePaint = new LinkedList<CustomShape>();
        ArrayList<int[]> emptyPaint = new ArrayList<int[]>();
        ArrayList<int[]> eaglePaint = new ArrayList<int[]>();
        ArrayList<int[]> sharkPaint = new ArrayList<int[]>();

        for (int[] key : paintBeforeChange.keySet()) {
            if (paintBeforeChange.get(key) == null) {
                emptyPaint.add(key);
            } else if (paintBeforeChange.get(key) == Player.EAGLE) {
                eaglePaint.add(key);
            } else if (paintBeforeChange.get(key) == Player.SHARK) {
                sharkPaint.add(key);
            }
        }
        reversePaint.push(new CustomShape(emptyPaint));
        reversePaint.push(new CustomShape(eaglePaint));
        reversePaint.push(new CustomShape(sharkPaint));
        return reversePaint;
    }

    public Piece getActionPiece() {
        return actionPiece;
    }
}
