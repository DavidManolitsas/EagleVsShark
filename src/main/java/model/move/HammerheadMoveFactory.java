package main.java.model.move;

import main.java.model.board.Board;
import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.movements.MoveUp;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.TShape;

import java.util.ArrayList;
import java.util.List;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class HammerheadMoveFactory
        implements MoveFactory {

    @Override
    public List<Move> generateMoves(int startRow, int startCol, boolean isPowered, Board board) {
        int squaresMoved = 1;
        int length = 3;
        int width = 1;

        List<Move> moveList = new ArrayList<>();
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
        return moveList;
    }

}
