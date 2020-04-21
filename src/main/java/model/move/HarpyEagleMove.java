package main.java.model.move;

import main.java.model.move.movements.*;
import main.java.model.move.shape.VShape;

public class HarpyEagleMove extends PieceMove {

    private static final int SQUARE_MOVED = 3;

    public HarpyEagleMove(int startRow, int startCol) {
        MoveLeft left = new MoveLeft(startRow, startCol, SQUARE_MOVED);
        MoveRight right = new MoveRight(startRow, startCol, SQUARE_MOVED);
        MoveDown down = new MoveDown(startRow, startCol, SQUARE_MOVED);
        MoveUp up = new MoveUp(startRow, startCol, SQUARE_MOVED);

        moveList.add(new Move(left,
                new VShape(left.getDestination(), VShape.DIRECTION_LEFT)));
        moveList.add(new Move(right,
                new VShape(right.getDestination(), VShape.DIRECTION_RIGHT)));
        moveList.add(new Move(up,
                new VShape(up.getDestination(), VShape.DIRECTION_UP)));
        moveList.add(new Move(down,
                new VShape(down.getDestination(), VShape.DIRECTION_DOWN)));
    }

}
