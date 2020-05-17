package main.java.model.move;

import main.java.model.move.movements.MoveDown;
import main.java.model.move.movements.MoveLeft;
import main.java.model.move.movements.MoveRight;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.SquareShape;

public class BaldEagleMove
        extends PieceMove {

    private int squaresMoved = 1;

    /*
     * Precondition: none
     *              Starting point of the piece
     * Postcondition: a list of move
     */
    public BaldEagleMove(int startRow, int startCol, boolean isPowered) {
        if (isPowered) {
            squaresMoved = squaresMoved * 3;
        }

        Movements[] movements = {
                new MoveLeft(startRow, startCol, squaresMoved),
                new MoveLeft(startRow, startCol, squaresMoved + 1),
                new MoveLeft(startRow, startCol, squaresMoved + 2),
                new MoveRight(startRow, startCol, squaresMoved),
                new MoveRight(startRow, startCol, squaresMoved + 1),
                new MoveRight(startRow, startCol, squaresMoved + 2),
                new MoveDown(startRow, startCol, squaresMoved),
                new MoveDown(startRow, startCol, squaresMoved + 1),
                new MoveDown(startRow, startCol, squaresMoved + 2)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new SquareShape(movement.getDestination(), false)));
        }
    }
}
