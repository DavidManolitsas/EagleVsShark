package main.java.model.move;

import java.util.ArrayList;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.shape.TriangleShape;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class SawSharkMove extends PieceMove {


    public SawSharkMove(int startRow, int startCol, int width, int squaresMoved) {
        super(squaresMoved);
        moveList = new ArrayList<>();
        moveList.add(new Move(new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, width, TriangleShape.DIRECTION_LEFT)));
        moveList.add(new Move(new MoveDiagonalRight(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, width, TriangleShape.DIRECTION_RIGHT)));
    }
}
