package main.java.model.move;

import main.java.model.move.movements.*;
import main.java.model.move.shape.SquareShape;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class GoblinSharkMove
        extends PieceMove {

    private int squaresMoved = 1;

    public GoblinSharkMove(int startRow, int startCol, boolean isPowered) {
        if (isPowered) {
            squaresMoved = squaresMoved * 2;
        }

        Movements[] movements = {
                new MoveLeft(startRow, startCol, squaresMoved),
                new MoveRight(startRow, startCol, squaresMoved),
                new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                new MoveDiagonalRight(startRow, startCol, squaresMoved)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new SquareShape(movement.getDestination(), isPowered)));
        }
    }
}
