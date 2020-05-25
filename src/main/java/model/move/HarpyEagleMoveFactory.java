package main.java.model.move;

import main.java.model.board.Board;
import main.java.model.move.movements.MoveDown;
import main.java.model.move.movements.MoveLeft;
import main.java.model.move.movements.MoveRight;
import main.java.model.move.movements.MoveUp;
import main.java.model.move.shape.VShape;

import java.util.ArrayList;
import java.util.List;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class HarpyEagleMoveFactory
        implements MoveFactory {

    @Override
    public List<Move> generateMoves(int startRow, int startCol, boolean isPowered, Board board) {
        int squaresMoved = 3;
        List<Move> moveList = new ArrayList<>();
        if (isPowered) {
            squaresMoved = 1;
        }

        MoveLeft left = new MoveLeft(startRow, startCol, squaresMoved);
        MoveRight right = new MoveRight(startRow, startCol, squaresMoved);
        MoveDown down = new MoveDown(startRow, startCol, squaresMoved);
        MoveUp up = new MoveUp(startRow, startCol, squaresMoved);

        moveList.add(new Move(left,
                              new VShape(left.getDestination(), VShape.DIRECTION_LEFT, isPowered)));
        moveList.add(new Move(right,
                              new VShape(right.getDestination(), VShape.DIRECTION_RIGHT, isPowered)));
        moveList.add(new Move(up,
                              new VShape(up.getDestination(), VShape.DIRECTION_UP, isPowered)));
        moveList.add(new Move(down,
                              new VShape(down.getDestination(), VShape.DIRECTION_DOWN, isPowered)));

        return moveList;
    }
}