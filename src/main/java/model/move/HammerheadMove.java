package main.java.model.move;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.movements.MoveUp;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.TShape;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class HammerheadMove
        extends PieceMove {

    private int squaresMoved = 1;
    private int length = 3;
    private int width = 1;

    public HammerheadMove(int startRow, int startCol, boolean isPowered) {
        if (isPowered) {
            squaresMoved = squaresMoved * 3;
            squaresMoved += 1;
            width += 1;
        }

        Movements[] movements = {
                new MoveUp(startRow, startCol, squaresMoved),
                new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                new MoveDiagonalRight(startRow, startCol, squaresMoved)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new TShape(movement.getDestination(), length, width)));
        }
    }

}
