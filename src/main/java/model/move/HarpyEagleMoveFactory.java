package main.java.model.move;

import main.java.model.board.Board;
import main.java.model.move.movements.*;
import main.java.model.move.shape.VShape;

import java.util.ArrayList;
import java.util.List;

public class HarpyEagleMoveFactory
        implements MoveFactory {

    @Override
    public List<Move> generateMoves(int startRow, int startCol, boolean isPowered, Board board) {
        int squaresMoved = 3;
        List<Move> moveList = new ArrayList<>();
        if (isPowered) {
            squaresMoved = 1;
        }

        Movements left = new MoveLeft(startRow, startCol, squaresMoved);
        Movements right = new MoveRight(startRow, startCol, squaresMoved);
        Movements down = new MoveDown(startRow, startCol, squaresMoved);
        Movements up = new MoveUp(startRow, startCol, squaresMoved);

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
