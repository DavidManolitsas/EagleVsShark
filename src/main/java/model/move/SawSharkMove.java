package main.java.model.move;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class SawSharkMove
        extends PieceMove {

    private int squaresMoved = 2;
    private int width = 3;

    public SawSharkMove(int startRow, int startCol, boolean isPowered) {
        if (isPowered) {
            squaresMoved += 1;
            width += 1;
        }
        moveList = new ArrayList<>();
        moveList.add(new Move(new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, width, TriangleShape.DIRECTION_LEFT)));
        moveList.add(new Move(new MoveDiagonalRight(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, width, TriangleShape.DIRECTION_RIGHT)));
    }
}
