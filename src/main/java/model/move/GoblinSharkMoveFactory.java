package main.java.model.move;

import main.java.model.board.Board;
import main.java.model.move.movements.*;
import main.java.model.move.shape.SquareShape;

import java.util.ArrayList;
import java.util.List;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class GoblinSharkMoveFactory
        implements MoveFactory {

    @Override
    public List<Move> generateMoves(int startRow, int startCol, boolean isPowered, Board board) {
        int squaresMoved = 1;
        List<Move> moveList = new ArrayList<>();
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
        return moveList;
    }
}
