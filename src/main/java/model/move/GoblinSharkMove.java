package main.java.model.move;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.movements.MoveLeft;
import main.java.model.move.movements.MoveRight;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.SquareShape;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class GoblinSharkMove extends PieceMove {

    public GoblinSharkMove(int startRow, int startCol, int squaresMoved, boolean isPowered) {
        super(squaresMoved);

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
