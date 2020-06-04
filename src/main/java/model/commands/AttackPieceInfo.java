package main.java.model.commands;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import main.java.model.piece.Piece;

import java.util.LinkedList;

public class AttackPieceInfo {
    private LinkedList<Piece> attackedPieces;
    private LinkedList<int[]> previousPositions;
    private LinkedList<int[]> newPositions;

    public AttackPieceInfo() {
        attackedPieces = new LinkedList<>();
        previousPositions = new LinkedList<>();
        newPositions = new LinkedList<>();
    }

    @Requires("piece != null && previousPosition.length() == 2 && newPosition.length() == 2")
    @Ensures("attackedPiece.size() == previousPositions.size() == newPositions.size()")
    public void addAttackedPiece(Piece piece, int[] previousPosition, int[] newPosition) {
        attackedPieces.push(piece);
        previousPositions.push(previousPosition);
        newPositions.push(newPosition);
    }

    public LinkedList<Piece> getAttackedPieces() {
        return attackedPieces;
    }

    public LinkedList<int[]> getPreviousPositions() {
        return previousPositions;
    }

    public LinkedList<int[]> getNewPositions() {
        return newPositions;
    }
}
